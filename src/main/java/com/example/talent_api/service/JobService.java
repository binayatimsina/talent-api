package com.example.talent_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
