package com.example.talent_api.controller_tests;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ExceptionCollector;

import com.example.talent_api.controller.JobController;
import com.example.talent_api.model.Candidate;
import com.example.talent_api.model.Job;
import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;
import com.example.talent_api.service.JobService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobControllerTests {
    @Mock 
    private JobService jobservice;

    @InjectMocks 
    private JobController jobcontroller;

    User user = new User("julian", "password", "user");
    Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
    Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");

    @Test
    public void getAllJobs () {
        Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");
        Job job2 = new Job(manager, "COrporate", "CEO", "Developer", "Description", "Five Years On Job", "Agency");
        given(jobservice.getAlljobs()).willReturn(List.of(job, job2));
        var response = jobcontroller.getAlljobs();
        List<Job> jobs = response.getBody();
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isEqualTo(2);
    }
    @Test
    public void testgetJobsByID() {
        Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");
        Job job2 = new Job(manager, "COrporate", "CEO", "Developer", "Description", "Five Years On Job", "Agency");
        given(jobservice.getJobById(2l)).willReturn(Optional.ofNullable(job));
    }
    @Test
    public void testaddJob() throws Exception {
        User u1 = new User("user1","pass1","admin");
        Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
        Job job = new Job();
        job.setManager(manager);
        job.setDepartment("Finance");
        job.setJob_title("Banker");
        job.setListing_title("Full time banker");
        
        when(jobservice.addJob(any(Job.class))).thenReturn(job);
        var response = jobcontroller.addJob(job).getBody();
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(job);
    }
    @Test
    public void testupdateJOb() {
        Job updatedJob = new Job(manager, "Business", "Lead", "Analyst", "Checks data", "5 year", "NEW");
        given(jobservice.updateJob(2l, updatedJob)).willReturn(updatedJob);
        var j = jobcontroller.updateJob(2l, updatedJob);
        
        assertThat(updatedJob).isNotNull();
        assertThat(j.getBody()).isEqualTo(updatedJob);
    }
    @Test
    public void deleteJob() {
        User u1 = new User("user1","pass1","admin");
        Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
        Job job = new Job();
        job.setManager(manager);
        job.setDepartment("Finance");
        job.setJob_title("Banker");
        job.setListing_title("Full time banker");
        //us.addUser(u1);
        //when
        //the repository method and what it should return
        given(jobservice.deleteJob(job.getId()));
        //the return is Optional<Customer> so it should be nullable in case null returns
        //the service method calling the repo...
        var resp = jobcontroller.deleteJob(job.getId());
        //then
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody()).isEqualTo(true);
        

    }

    void testGetJobsBySearchTerm() {

        User user = new User("julian", "password", "user");
        Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
        Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");

        given(jobservice.searchFromJobs("user1")).willReturn(List.of(job));
        var cList = jobcontroller.searchFromJobs("user1");
        //then
        assertThat(cList).isNotNull();
        assertThat(cList.getBody().size()).isEqualTo(1);

    }
    void testGetJobById(){
        User user = new User("julian", "password", "user");
        Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
        Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");
        

        //when
         given(jobservice.getJobById(2l)).willReturn(Optional.ofNullable(job));
        //the return is Optional<Customer> so it should be nullable in case null returns
        var jobs = jobcontroller.getJobById(2l);
        //then
        assertThat(jobs.getBody()).isNotNull();
        assertThat(jobs.getBody()).isEqualTo(Optional.ofNullable(job));
    }
    /*
    @Test
    void testDeleteJob(){
        Job j1 = new Job(manager, "Business", "Lead", "Analyst", "Checks data", "5 year", "NEW");
        //when
        //the repository method and what it should return
        given(jobservice.deleteJob(j1.getId())).willReturn(true);
        //the return is Optional<Customer> so it should be nullable in case null returns
        //the service method calling the repo...
        var resp = managerController.deleteManager(m1.getId());
        //then
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody()).isEqualTo(true); //comparing service return vals with repo return val

    }*/


}
