package com.example.talent_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{job_id}")
    public Optional<Job> getJobById(@PathVariable("job_id") int job_id){
        return jobService.getJobById(job_id);
    }

    @GetMapping("/open")
    public List<Job> getJobById(){
        return jobService.getOpenJobs();
    }

    @GetMapping("/manager/{mgr_id}")
    public List<Job> getJobsByManager(@PathVariable("mgr_id") int mgr_id){
        return jobService.getJobsByManager(mgr_id);
    }

}
