package com.example.talent_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.model.Job;
import com.example.talent_api.service.JobService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs APIs", description = "Operations related to jobs")
@CrossOrigin(origins="*")
public class JobController {

    @Autowired
    private JobService jobService;

    
    @GetMapping("")
    public ResponseEntity<List<Job>> getAlljobs(){
        List<Job> jobs = jobService.getAlljobs();
        if (!jobs.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(jobs);
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobs);
    }

    @GetMapping("/{job_id}")
    public ResponseEntity<Optional<Job>> getJobById(@PathVariable("job_id") Long job_id){
        Optional<Job> job = jobService.getJobById(job_id);
        if (job.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(job);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    }

    @GetMapping("/open")
    public ResponseEntity<List<Job>> getOpenJobs(){
        List<Job> jobs = jobService.getOpenJobs();
        if (!jobs.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(jobs);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobs);
    }

    @GetMapping("/open/manager/{mgr_id}")
    public ResponseEntity<List<Job>> getOpenJobsByManager(@PathVariable("mgr_id") int mgr_id){
        List<Job> jobs = jobService.getOpenJobsByManager(mgr_id);
        if (!jobs.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(jobs);
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobs);
    }
    
    @PostMapping("")
    public ResponseEntity<?> addJob(@RequestBody Job job){
        try {
            Job createdJob = jobService.addJob(job);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }  
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable("id") Long id, @RequestBody Job job){
        try {
            Job updatedJob = jobService.updateJob(id, job);
            return ResponseEntity.status(HttpStatus.OK).body(updatedJob);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteJob(@PathVariable("id") Long id){
        try {
            jobService.deleteJob(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        
    }

    @GetMapping("/search")
    public  ResponseEntity<List<Job>> searchFromJobs(@RequestParam("searchTerm") String searchTerm) {
        List<Job> jobs = jobService.searchFromJobs(searchTerm.toLowerCase());
        if (!jobs.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(jobs);
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobs);
    }

}
