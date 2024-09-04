package com.example.talent_api;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import com.example.talent_api.controller.JobController;
import com.example.talent_api.model.Job;
import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;
import com.example.talent_api.service.JobService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JobControllerTests {
    @Mock private JobService jobservice;
    @InjectMocks private JobController jobcontroller;
    User user = new User("julian", "password", "user");
    Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
    Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");

    @Test
    public void getAllJobs () throws Exception {
        Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");
        Job job2 = new Job(manager, "COrporate", "CEO", "Developer", "Description", "Five Years On Job", "Agency");
        given(jobservice.getAlljobs()).willReturn(List.of(job, job2));
        var jList = jobcontroller.getAlljobs();
        assertThat(jList).isNotNull();
        assertThat(jList.size()).isEqualTo(2);
    }
    @Test
    public void addJob() throws Exception {
        Job job = new Job(manager, "Finance", "Name", "Developer", "Description", "Five Years On Job", "Agency");
        given(jobservice.addJob(job)).willReturn(job);
        var j = jobcontroller.addJob(job);
        assertThat(j).isNotNull();
        assertThat(j).isEqualTo(job);
    }
    @Test
    public void testupdateJOb() throws Exception {
        Job updatedJob = new Job(manager, "Business", "Lead", "Analyst", "Checks data", "5 year", "NEW");
        given(jobservice.updateJob(2l, updatedJob)).willReturn(updatedJob);
        var j = jobcontroller.updateJob(2l, updatedJob);
        
        assertThat(updatedJob).isNotNull();
        assertThat(j).isEqualTo(updatedJob);
    }
    @Test
    public void deleteJob() throws Exception {
        doNothing().when(jobservice).deleteJob(2L);
        jobservice.deleteJob(2L);
        verify(jobservice, times(1)).deleteJob(2L);

    }


}
