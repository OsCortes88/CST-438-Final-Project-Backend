package com.project.gamestore.controller;

import com.project.gamestore.domain.User;
import com.project.gamestore.domain.UserRepository;
import com.project.gamestore.dto.UserSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignUpController {
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Boolean> insertUser(@RequestBody UserSignUp accountInfo) {
        // Attempt to see if passed in user already exists.
        User doesUserExist = userRepository.findByEmail(accountInfo.email());
        // If it does not, proceed with sign up.
        if(doesUserExist == null) {
            User newUser = new User();
            newUser.setEmail(accountInfo.email());
            newUser.setFirstName(accountInfo.firstName());
            newUser.setLastName(accountInfo.lastName());
            // Use BCrypt to encode password.
            newUser.setPassword(passwordEncoder.encode(accountInfo.password()));
            newUser.setRole("USER");
            userRepository.save(newUser);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        // Return false if user already exists
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}