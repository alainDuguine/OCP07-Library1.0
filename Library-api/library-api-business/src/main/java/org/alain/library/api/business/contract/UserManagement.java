package org.alain.library.api.business.contract;


import org.alain.library.api.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserManagement extends CrudManagement<User>{
    List<User> findUsersByMail(String email);
    Optional<User> saveUser(User user);
    Optional<User> updateUser(Long id, User userForm, String authorization);
    boolean checkUserCredentialsFromB64Encoded(String userCredentials, String authorization);
    void deleteUser(Long id);
    Optional<User> findUserByIdWithAuthorization(Long id, String authorization);
    boolean login(String email, String password);
}
