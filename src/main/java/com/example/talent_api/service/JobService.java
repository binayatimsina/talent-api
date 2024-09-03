package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.talent_api.model.Job;
import com.example.talent_api.repository.JobRepository;



@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAlljobs(){
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(long job_id){
        return jobRepository.findById(job_id);
    }

    public List<Job> getOpenJobs() {
        return jobRepository.findOpenJobs();
    }

    public List<Job> getOpenJobsByManager(long manager_id) {
        return jobRepository.getOpenJobsByManager(manager_id);
    }

}
