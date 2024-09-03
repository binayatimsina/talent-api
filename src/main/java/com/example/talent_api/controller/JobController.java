package com.example.talent_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.talent_api.model.Job;
import com.example.talent_api.service.JobService;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins="*")
public class JobController {

    @Autowired
    private JobService jobService;

    
    @GetMapping("")
    public List<Job> getAlljobs(){
        return jobService.getAlljobs();
    }


}
