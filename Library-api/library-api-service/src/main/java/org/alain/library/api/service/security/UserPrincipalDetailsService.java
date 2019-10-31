//package org.alain.library.api.service.security;
//
//
//import org.alain.library.api.consumer.repository.UserRepository;
//import org.alain.library.api.model.user.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserPrincipalDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public UserPrincipalDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String s){
//        Optional<User> user = userRepository.findByEmail(s);
//        if(!user.isPresent()){
//            throw new UsernameNotFoundException("Utilisateur inconnu :" + s);
//        }
//        return new UserPrincipal(user.get());
//    }
//}
