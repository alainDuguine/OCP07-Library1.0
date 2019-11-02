package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.UserManagement;
import org.alain.library.api.business.exceptions.UnauthorizedException;
import org.alain.library.api.consumer.repository.UserRepository;
import org.alain.library.api.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class UserManagementImpl extends CrudManagerImpl<User> implements UserManagement {

    private final UserRepository userRepository;

    public UserManagementImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findUserByMail(String email) {
        return userRepository.findByEmailLike("%"+email+"%");
    }

    @Override
    public Optional<User> saveUser(User user) {
        if (this.findUserByMail(user.getEmail()).isEmpty()){
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles("USER");
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUser(Long id, User userForm, String authorization) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            // Check authorization
            String userCredentials = user.get().getEmail() + ':' + user.get().getPassword();
            authorization = authorization.replace("Basic ","");
            if(userCredentials.equals(decodeAuthorization(authorization))){
                user.get().setFirstName(userForm.getFirstName());
                user.get().setLastName(userForm.getLastName());
                user.get().setPasswordConfirmation(user.get().getPassword());
                return Optional.of(userRepository.save(user.get()));
            }else{
                throw new UnauthorizedException();
            }
        }
        return Optional.empty();
    }

    private String decodeAuthorization(String encodedAuthorization){
        byte[] decodedAuthorization = Base64.getDecoder().decode(encodedAuthorization);
        return new String(decodedAuthorization);
    }

}
