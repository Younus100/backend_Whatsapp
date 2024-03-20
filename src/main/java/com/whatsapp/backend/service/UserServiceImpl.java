package com.whatsapp.backend.service;

import com.whatsapp.backend.config.JwtProvider;
import com.whatsapp.backend.config.SecurityUtils;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.UserRepository;
import com.whatsapp.backend.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider tokenProvider;

    @Override
    public User findUserById(Long id) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = tokenProvider.getemailFromToken(jwt);
        if(email==null) { throw  new UserException("Email not found"); }
        User user = userRepository.findByEmail(email);
        if(user==null) { throw  new UserException("Email not found"); }
        return  user;
    }

    @Override
    public User updateUser( UserRequest req) throws UserException {
        // Find the user by id
        User user = SecurityUtils.getCurrentUser(this);

        // Update user fields based on the UserRequest
        if (req.getFull_name() != null) {
            user.setFullName(req.getFull_name());
        }

        if (req.getPassword() != null) {
            user.setPassword(req.getPassword());
        }

        // Save the updated user
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        query = "%"+query+"%";
        return userRepository.findByFullNameLikeIgnoreCaseOrEmailLikeIgnoreCase(query,query);
    }

    @Override
    public User findUserByEmail(String email) {
       User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public void updateProfilePicofUser(String url) throws UserException {
        User user = SecurityUtils.getCurrentUser(this);
        user.setProfilePicture(url);
        userRepository.save(user);
    }


}
