package org.alain.library.api.model.user.validation;

import org.alain.library.api.model.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        User user = (User) o;
        if(user.getPassword() == null){
            return false;
        }
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}
