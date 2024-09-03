package com.example.talent_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public Optional<Application> getAllApplicationById(@PathVariable("id") Long id){
        return applicationService.getApplicationById(id);
    }

    @GetMapping("/manager/{manager_id}")
    public List<Application> getAllApplicationByManagerId(
            @PathVariable("manager_id") Long id){
        return applicationService.getApplicationByManagerId(id);
    }

    @GetMapping("/job/{job_id}")
    public Optional<Application> getAllApplicationByJobId(
            @PathVariable("job_id") Long id){
        return applicationService.getApplicationByJobId(id);
    }

}
