package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.model.Application;
import com.example.talent_api.repository.ApplicationRepository;


@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications(){
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(Long appId){
        Optional<Application> appplicationsById = applicationRepository.findById(appId);
        return appplicationsById;
    }

    public List<Application> getApplicationByManagerId(Long managerId){
        List<Application> appplicationByManagerId = 
            applicationRepository.findByJob_Manager_Id(managerId);
        return appplicationByManagerId;
    }

    public Optional<Application> getApplicationByJobId(Long jobId){
        Optional<Application> appplicationByJobId = 
            applicationRepository.findByJob_Id(jobId);
        return appplicationByJobId;
    }

    public Optional<Application> getApplicationByUserId(Long userId){
        Optional<Application> appplicationByUserId = 
            applicationRepository.findByUser_Id(userId);
        return appplicationByUserId;
    }

}
