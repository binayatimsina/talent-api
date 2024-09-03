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

    public List<Job> searchFromJobs(String searchTerm){
        return jobRepository.searchFromJobs(searchTerm);
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
    
    public Job addJob(Job job){
        Job savedJob = jobRepository.save(job);
        return savedJob;
    }

    public Job updateJob(Long id, Job job){
        Job existingJob = jobRepository.getById(id);
        existingJob.setJob_description(job.getJob_description());
        existingJob.setJob_title(job.getJob_title());
        existingJob.setListing_status(job.getListing_status());
        existingJob.setListing_title(job.getListing_title());
        existingJob.setManager(job.getManager());
        existingJob.setAdditional_information(job.getAdditional_information());
        existingJob.setDate_closed(job.getDate_closed());
        existingJob.setDate_listed(job.getDate_listed());
        existingJob.setDepartment(job.getDepartment());
        Job savedJob = jobRepository.save(existingJob);
        return savedJob;
    }

    public void deleteJob (Long id){
        jobRepository.deleteById(id);
    }

}
