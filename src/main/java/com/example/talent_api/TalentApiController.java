package com.example.talent_api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TalentApiController {

    @GetMapping("/")
    public String greeting() {
		return "This is the talent Api!";
	}
    
}
