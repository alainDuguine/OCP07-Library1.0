package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.UserManagement;
import org.alain.library.api.consumer.repository.UserRepository;
import org.alain.library.api.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;

    public UserManagementImpl(UserRepository userRepository1) {
          this.userRepository = userRepository1;
    }

    @Override
    public Optional<User> findOne(Long id){
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserByMail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == null) {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles("USER");
        }
        return userRepository.save(user);
    }

}
