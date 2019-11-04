package org.alain.library.api.business.contract;


import org.alain.library.api.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserManagement extends CrudManagement<User> {
    List<User> findUserByMail(String email);
    Optional<User> saveUser(User user);
    Optional<User> updateUser(Long id, User userForm, String authorization);
}
