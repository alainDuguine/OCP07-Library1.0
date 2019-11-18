package org.alain.library.api.service;

import org.alain.library.api.business.contract.UserManagement;
import org.alain.library.api.consumer.repository.*;
import org.alain.library.api.model.loan.*;
import org.alain.library.api.model.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"org.alain.library.api"})
@EntityScan(basePackages = {"org.alain.library.api"})
@EnableJpaRepositories(basePackages = {"org.alain.library.api"})
@EnableSwagger2
public class LibraryApiServiceApplication implements CommandLineRunner {

    private final StatusRepository statusRepository;
    private final UserManagement userManagement;

    public LibraryApiServiceApplication(StatusRepository statusRepository, UserManagement userManagement) {
        this.statusRepository = statusRepository;
        this.userManagement = userManagement;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiServiceApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args){
        List<Status> statusList = statusRepository.findAll();
        if (statusList.size() != StatusDesignation.values().length) {
            for (StatusDesignation statusDesignation : StatusDesignation.values()) {
                Status status = new Status(statusDesignation);
                statusRepository.save(status);
            }
            User user1 = new User("alain_duguine@hotmail.fr", "user","user","Alain", "Duguine");
            User user2 = new User("admin@openlibrary.fr", "admin","admin","Admin", "Library");
            user2.setRoles("USER,ADMIN");
            userManagement.saveUser(user1);
            userManagement.saveUser(user2);
        }
    }

//    @Bean
//    public ServletWebServerFactory servletContainer(){
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context){
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//        return tomcat;
//    }
//
//    private Connector httpToHttpsRedirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8082);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
}
