package com.example.talent_api.service;

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
}
