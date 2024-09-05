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

    public List<Application> getApplicationByJobId(Long jobId){
        List<Application> appplicationByJobId = 
            applicationRepository.findByJob_Id(jobId);
        return appplicationByJobId;
    }

    public List<Application> getApplicationByUserId(Long userId){
        List<Application> appplicationByUserId = 
            applicationRepository.findByUser_Id(userId);
        return appplicationByUserId;
    }

    public Application addApplication (Application application){
        return applicationRepository.save(application);
    }

    public Application updateApplication (Long id, Application application){
        Application existingApplication = applicationRepository.getById(id);
        existingApplication.setDate_applied(application.getDate_applied());
        existingApplication.setJob(application.getJob());
        existingApplication.setUser(application.getUser());
        existingApplication.setApplication_status(application.getApplication_status());
        existingApplication.setCover_letter(application.getCover_letter());
        existingApplication.setCustom_resume(application.getCustom_resume());
        return applicationRepository.save(existingApplication);
    }

    public boolean deleteApplication (Long id){
        if(applicationRepository.existsById(id)){
            applicationRepository.deleteById(id);
            return true;
        }
        return false;
        
    }

}
