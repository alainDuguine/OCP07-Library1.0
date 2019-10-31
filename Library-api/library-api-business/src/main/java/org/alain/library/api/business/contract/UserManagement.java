package org.alain.library.api.business.contract;


import org.alain.library.api.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserManagement{

    Optional<User> findOne(Long id);
    List<User> findAll();
    void delete(Long id);
    Optional<User> findUserByMail(String email);
    User saveUser(User user);
}
