package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.InvalidLoginException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String splitAuthorizationBearer(String header) {
        return header.split(" ")[1];
    }

    public boolean validateJwtToken(String token) {
        return jwtService.verifyToken(token);
    }

    public Boolean isUserExist(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    public User register(String username, String password) throws UserAlreadyExistsException {
        // check user exists by username
        if (isUserExist(username) == true) {
            throw new UserAlreadyExistsException();
        }

        // create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // save and return new user
        return userRepository.saveAndFlush(user);
    }

    public User login(String username, String password) throws UserNotFoundException, InvalidLoginException {
        // get user by username
        User user = userRepository.findUserByUsername(username);

        // check user null
        if (user == null) {
            throw new UserNotFoundException();
        }

        // check password compare
        if (passwordEncoder.matches(password, user.getPassword()) == false) {
            throw new InvalidLoginException();
        }

        // return user
        return user;
    }

    public User getUserFromAccessToken(String accessToken) throws UserNotFoundException {
        Integer userId = jwtService.getUserIdFromAccessToken(accessToken);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
    }
}
