package org.alain.library.api.webservice.security;


import org.alain.library.api.consumer.repository.UserRepository;
import org.alain.library.api.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s){
        User user = userRepository.findByEmail(s);
        if(user==null){
            throw new UsernameNotFoundException("Utilisateur inconnu :" + s);
        }
        return new UserPrincipal(user);
    }
}
