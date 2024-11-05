package com.whatsapp.backend.config;

import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getCurrentUser(UserService userService) throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();
            return userService.findUserByEmail(email);
        } else {
            throw  new UserException("User Not Found Exception"); // or throw an exception, depending on your requirements
        }
    }
}
