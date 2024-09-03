package com.example.talent_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.model.Application;
import com.example.talent_api.service.ApplicationService;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins="*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    
    @GetMapping("")
    public List<Application> getAllApplications(){
        //push testing
        return applicationService.getAllApplications();
    }
}
