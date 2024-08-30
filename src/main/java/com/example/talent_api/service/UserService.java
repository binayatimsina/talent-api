package com.example.talent_api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.UserRepository;
import com.example.talent_api.model.User;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public Object addUser(User user) {
        
        return (userRepository.save(user)); 
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User updateUser(int id, User user) {
        User currentUser =  (User) userRepository.findById(id).get();
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(user.getPassword());
        currentUser.setType(user.getType());
        userRepository.save(currentUser);
        return currentUser;

    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
