package com.example.JwtAuth.services;

import com.example.JwtAuth.models.User;
import com.example.JwtAuth.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    //dependency injection
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor injection of UserRepository
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean registerUser(User user){
        //check if a user with the same email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }

        //hash the password before saving to db
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //save the user to mongodb
        userRepository.save(user);

        return true; // Registration successful
    }
}
