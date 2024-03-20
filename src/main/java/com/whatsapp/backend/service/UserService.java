package com.whatsapp.backend.service;

import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.UserRequest;

import java.util.List;
import java.util.Set;

public interface UserService {
    User findUserById(Long id) throws UserException;

    User findUserProfile(String jwt) throws UserException;

    User updateUser(UserRequest req) throws UserException;

    List<User> searchUser(String query);

    User  findUserByEmail(String email) ;

    void updateProfilePicofUser(String url) throws UserException;

}
