package org.alain.library.api.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.alain.library.api.business.contract.UserManagement;
import org.alain.library.api.business.exceptions.UnauthorizedException;
import org.alain.library.api.model.user.User;
import org.alain.library.api.service.dto.UserDto;
import org.alain.library.api.service.dto.UserForm;
import org.alain.library.api.service.dto.UserFormUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.alain.library.api.service.api.Converters.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-31T15:23:24.407+01:00")

@Controller
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;
    private final UserManagement userManagement;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, UserManagement userManagement, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.userManagement = userManagement;
        this.request = request;
    }

    public ResponseEntity<UserDto> getUser(@ApiParam(value = "Id of user to return", required = true) @PathVariable("id") Long id) {
        Optional<User> userModel = userManagement.findOne(id);
        if (userModel.isPresent()) {
            return new ResponseEntity<UserDto>(convertUserModelToUserDto(userModel.get()), HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<UserDto>> getUsers(@ApiParam(value = "Email of user to return", defaultValue = "") @Valid @RequestParam(value = "email", required = false, defaultValue = "") String email) {
        List<User> userList = userManagement.findUserByMail(email);
        return new ResponseEntity<List<UserDto>>(convertListUsersModelToListUsersDto(userList), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(@ApiParam(value = "User id to delete", required = true) @PathVariable("id") Long id) {
        try {
            userManagement.deleteUser(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (UnauthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

    public ResponseEntity<UserDto> addUser(@ApiParam(value = "User object that needs to be added to the database", required = true) @Valid @RequestBody UserForm userForm) {
        Optional<User> userModel = userManagement.saveUser(convertUserFormToUserModel(userForm));
        if (userModel.isPresent()) {
            return new ResponseEntity<UserDto>(convertUserModelToUserDto(userModel.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<UserDto>(HttpStatus.CONFLICT);
    }

    public ResponseEntity<UserDto> updateUser(@ApiParam(value = "User id to update", required = true) @PathVariable("id") Long id,
                                              @ApiParam(value = "User object to update", required = true) @Valid @RequestBody UserFormUpdate userFormUpdate,
                                              @ApiParam(value = "User identification", required = true) @RequestHeader(value = "Authorization", required = true) String authorization) {
        try {
            Optional<User> userModel = userManagement.updateUser(id, convertUserFormUpdateToUserModel(userFormUpdate), authorization);
            if (userModel.isPresent()) {
                return new ResponseEntity<UserDto>(convertUserModelToUserDto(userModel.get()), HttpStatus.OK);
            }
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to update this user");
        }
    }
}