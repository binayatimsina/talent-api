package com.example.talent_api.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.service.UserService;
import com.example.talent_api.security.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.talent_api.model.User;
import java.util.*;

@RestController
@CrossOrigin(origins="*")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    } 

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginCredential) {
    String username = loginCredential.get("username");
    String password = loginCredential.get("password");

    User userDetails = userService.findUserByUsername(username, password);

    if (userDetails != null) {
        // Create authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token
        String token = tokenService.generateToken(authentication);

        // Return the token along with user information
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);

        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody Map<String, String> registrationCredential) {
        String username = registrationCredential.get("username");
        String password = registrationCredential.get("password");
        String type = registrationCredential.get("type");
        
        User newUser = userService.register(username, password, type);

        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exists.");
        }


    }

}
