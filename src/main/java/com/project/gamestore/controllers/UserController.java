package com.project.gamestore.controllers;

import com.project.gamestore.domain.User;
import com.project.gamestore.domain.UserRepository;
import com.project.gamestore.dto.UserInfo;
import com.project.gamestore.dto.UserSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/user/{email}")
    @CrossOrigin
    public UserInfo getUserInfo(@PathVariable("email") String email){
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found.");
        } else {
            return new UserInfo(user.getId(), email, user.getFirstName(), user.getLastName());
        }
    }
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
            newUser.setPassword(accountInfo.password());
            newUser.setRole("USER");
            userRepository.save(newUser);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        // Return false if user already exists
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
