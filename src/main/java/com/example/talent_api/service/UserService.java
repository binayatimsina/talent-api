package com.example.talent_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        System.out.println("-----------"+userRepository.findAll());
        return userRepository.findAll();
    }
    
}
