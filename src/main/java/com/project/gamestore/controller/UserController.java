package com.project.gamestore.controller;

import com.project.gamestore.domain.User;
import com.project.gamestore.domain.UserRepository;
import com.project.gamestore.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{email}")
    @CrossOrigin
    public UserInfo getUserInfo(@PathVariable("email") String email){
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found.");
        } else {
            return new UserInfo(email, user.getFirstName(), user.getLastName());
        }
    }
}
