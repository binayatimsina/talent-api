package com.example.talent_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.model.Application;
import com.example.talent_api.service.ApplicationService;

@RestController
@RequestMapping("/applications")
@Tag(name = "Application APIs", description = "Operations related to applications")
@CrossOrigin(origins="*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    
    @GetMapping("")
    public ResponseEntity<List<Application>> getAllApplications(){
        List<Application> applicationList = applicationService.getAllApplications();
        if(applicationList.size()!=0){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(applicationList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(applicationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Application>> getAllApplicationById(@PathVariable("id") Long id){
        Optional<Application> applicationById = applicationService.getApplicationById(id);
        if(applicationById.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(applicationById);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    }

    @GetMapping("/manager/{manager_id}")
    public ResponseEntity<List<Application>> getAllApplicationByManagerId(
            @PathVariable("manager_id") Long id){
        
        List<Application> applicationList = applicationService.getApplicationByManagerId(id);
        if(applicationList.size() != 0){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(applicationList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(applicationList);
    }

    @GetMapping("/job/{job_id}")
    public ResponseEntity<Optional<Application>> getAllApplicationByJobId(
            @PathVariable("job_id") Long id){

        Optional<Application> applicationByJobId = applicationService.getApplicationByJobId(id);

        if(applicationByJobId.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(applicationByJobId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Optional<Application>> getAllApplicationByUserId(
            @PathVariable("user_id") Long id){

        Optional<Application> applicationByUserId = applicationService.getApplicationByUserId(id);

        if(applicationByUserId.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(applicationByUserId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    }

    @PostMapping("")
    public ResponseEntity<?> addApplication(@RequestBody Application application){
        try{
            Application savedApplication = applicationService.addApplication(application);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Integrity Violation Exception ");
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable Long id, @RequestBody Application application){
        try{
            Application updatedApplication = applicationService.updateApplication(id, application);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedApplication);
        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Integrity Violation Exception ");
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteApplication(@PathVariable Long id){
        if(applicationService.deleteApplication(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        
    }

}
