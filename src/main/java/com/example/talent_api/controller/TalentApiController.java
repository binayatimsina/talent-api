package com.example.talent_api.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.model.User;
import com.example.talent_api.service.UserService;

@RestController
@RequestMapping("/")
public class TalentApiController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String greeting() {
		return "This is the talent Api!";
	}

    @GetMapping("/users")
    public String getUsers() {
        return "Getting All users!";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") int id) {
        return "Getting user by id = "+id;
    }

    @PostMapping("/users")
    public Object addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
    
}
