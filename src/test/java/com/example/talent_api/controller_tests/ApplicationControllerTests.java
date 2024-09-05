package com.example.talent_api.controller_tests;
import com.example.talent_api.controller.ApplicationController;
import com.example.talent_api.model.Application;
import com.example.talent_api.model.Job;
import com.example.talent_api.model.User;
import com.example.talent_api.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTests {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private User sampleUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        // Set additional fields as needed
        return user;
    }

    private Job sampleJob() {
        Job job = new Job();
        job.setId(1L);
        job.setJob_title("Software Engineer");
        // Set additional fields as needed
        return job;
    }

    private Application sampleApplication() {
        Application application = new Application();
        application.setId(1L);
        application.setUser(sampleUser());
        application.setJob(sampleJob());
        application.setCover_letter("Sample Cover Letter");
        application.setCustom_resume("Sample Resume");
        application.setApplication_status("Pending");
        application.setDate_applied(LocalDateTime.now());
        return application;
    }

    @Test
    void contextLoads() {
        assertThat(applicationController).isNotNull();
    }

    @Test
    void testGetAllApplications() {
        Application app1 = sampleApplication();
        Application app2 = new Application(sampleUser(), sampleJob(), "Another Cover Letter", "Another Resume", "Accepted");

        when(applicationService.getAllApplications()).thenReturn(Arrays.asList(app1, app2));

        var response = applicationController.getAllApplications();
        List<Application> apps = response.getBody();

        assertThat(apps).isNotEmpty();
        assertThat(apps.size()).isEqualTo(2);
        assertThat(apps.get(0).getApplication_status()).isEqualTo("Pending");
        assertThat(apps.get(1).getApplication_status()).isEqualTo("Accepted");
    }

    @Test
    void testGetApplicationById() {
        Application app = sampleApplication();

        when(applicationService.getApplicationById(1L)).thenReturn(Optional.of(app));

        var response = applicationController.getAllApplicationById(1L);
        Optional<Application> respApp = response.getBody();

        assertThat(respApp).isPresent();
        assertThat(respApp.get().getApplication_status()).isEqualTo("Pending");
    }

    @Test
    void testGetAllApplicationsByManagerId() {
        Application app1 = sampleApplication();

        when(applicationService.getApplicationByManagerId(anyLong())).thenReturn(Arrays.asList(app1));

        var response  = applicationController.getAllApplicationByManagerId(1L);

        List<Application> applications = response.getBody();

        assertThat(applications).isNotEmpty();
        assertThat(applications.get(0).getApplication_status()).isEqualTo("Pending");
    }

    @Test
    void testGetApplicationByJobId() {
        Application app = sampleApplication();

        given(applicationService.getApplicationByJobId(1L)).willReturn(List.of(app));

        ResponseEntity<List<Application>> response  = applicationController.getAllApplicationByJobId(1L);

        List<Application> application = response.getBody();

        assertThat(application).isNotNull();
        assertThat(application.get(0).getApplication_status()).isEqualTo("Pending");
    }

    @Test
    void testGetApplicationByUserId() {
        Application app = sampleApplication();

        given(applicationService.getApplicationByUserId(1L)).willReturn(List.of(app));

        ResponseEntity<List<Application>> response  = applicationController.getAllApplicationByUserId(1L);
        List<Application> application = response.getBody();

        assertThat(application).isNotNull();
        assertThat(application.get(0).getApplication_status()).isEqualTo("Pending");
    }
}



