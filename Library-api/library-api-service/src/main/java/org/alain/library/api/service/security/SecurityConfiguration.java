package org.alain.library.api.service.security;

import org.alain.library.api.business.impl.UserManagementImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserManagementImpl userManagement;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfiguration(UserManagementImpl userManagement, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.userManagement = userManagement;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/loans/{id}/extension").authenticated()
                .antMatchers("/books/**").authenticated()
                .antMatchers("/authors/**").authenticated()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/loans/**").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userManagement);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
