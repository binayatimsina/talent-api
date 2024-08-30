package com.example.talent_api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TalentApiController {

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
    
}
