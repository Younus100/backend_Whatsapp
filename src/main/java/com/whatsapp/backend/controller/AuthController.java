package com.whatsapp.backend.controller;

import com.whatsapp.backend.config.JwtProvider;
import com.whatsapp.backend.config.SecurityUtils;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.UserRepository;
import com.whatsapp.backend.request.LoginRequest;
import com.whatsapp.backend.response.AuthResponse;
import com.whatsapp.backend.service.CustomerServiceImplementation;
import com.whatsapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtProvider  jwtProvider;

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private CustomerServiceImplementation customerServiceImplementation;

    public AuthController(JwtProvider jwtProvider, PasswordEncoder passwordEncoder, UserRepository userRepository, CustomerServiceImplementation customerServiceImplementation) {
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.customerServiceImplementation = customerServiceImplementation;
    }

    @PostMapping("/signup")
        public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullname =user.getFullName();
        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist!=null) {
            throw new UserException("Email Already Exist");
        }

        User createduser = new User();
        createduser.setEmail(email);
        createduser.setPassword(passwordEncoder.encode(password));
        createduser.setFullName(fullname);
        createduser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createduser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMsg("SignUp Success");
        authResponse.setFullName(user.getFullName());
        authResponse.setProfilepic(user.getProfilePicture());
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest)throws UserException {
        String  username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        User user = SecurityUtils.getCurrentUser(userService);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMsg("SignIn Success");
        authResponse.setFullName(user.getFullName());
        authResponse.setProfilepic(user.getProfilePicture());
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerServiceImplementation.loadUserByUsername(username);
        if(userDetails==null) {   throw  new BadCredentialsException("Invalid USerNAme");   }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw  new BadCredentialsException("Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null);
    }

}
