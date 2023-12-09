//package com.project.gamestore.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.gamestore.domain.User;
//import com.project.gamestore.domain.UserRepository;
//import com.project.gamestore.dto.AccountCredentials;
//import com.project.gamestore.dto.UserInfo;
//import com.project.gamestore.service.JwtService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class LoginController {
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
//        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
//        Authentication auth = authenticationManager.authenticate(creds);
//        // Verify use exists in database
//        User user = userRepository.findByEmail(auth.getName());
//        String jwts = jwtService.getToken(auth.getName());
//
//
//        // Build response with the generated token
//        return ResponseEntity.ok()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
//                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
//                .body(asJsonString(user));
//    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
