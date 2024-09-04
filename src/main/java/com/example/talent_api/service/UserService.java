package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.model.User;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Object addUser(User user) {
        
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByUsername(String username, String password) {
        // TODO Auto-generated method stub

        User currentUser = userRepository.findUserByUsername(username);
        if (currentUser != null) {
            if (currentUser.getPassword().equals(password)) {
                return currentUser;
            }
        } 

        return null;


    }

    public User register(String username, String password, String type) {
        // TODO Auto-generated method stub
        if (userRepository.findUserByUsername(username) != null) {
            return null; 
        } else {
            User newUser = new User(username, password, type);
            addUser(newUser);
            return newUser;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username, null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getType()) // Adjust based on your User roles/authorities setup
                .build();
    }
    
}
