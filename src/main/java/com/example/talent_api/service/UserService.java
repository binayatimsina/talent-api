package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.model.User;
import com.example.talent_api.model.UserPrincipal;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public User addUser(User user) {
        // String password = user.getPassword();
        // user.setPassword(passwordEncoder.encode(password));
        return (userRepository.save(user)); 
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User user) {
        User currentUser =  (User) userRepository.findById(id).get();
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(user.getPassword());
        currentUser.setType(user.getType());
        userRepository.save(currentUser);
        return currentUser;

    }

    public boolean deleteUser(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
        
    }

    public User findUserByUsername(String username, String password) {
        User currentUser = userRepository.findUserByUsername(username);
        if (currentUser != null) {
            System.out.println(currentUser.getPassword());
            // if (passwordEncoder.matches(password, currentUser.getPassword())) {
            //     return currentUser;
            // }
            if (password.equals(currentUser.getPassword())) {
                return currentUser;
            }
        } 

        return null;


    }

    public User register(String username, String password, String type) {
        if (userRepository.findUserByUsername(username) != null) {
            return null; 
        } else {
            // System.out.println(encryptedPassword);
            System.out.println("\n\n\n\n\n");
            User newUser = new User(username, password, type);
            addUser(newUser);
            return newUser;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            System.out.println("User not found!");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
    
}
