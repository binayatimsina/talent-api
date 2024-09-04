package com.example.talent_api.controller_integration_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;

import com.example.talent_api.controller.ApplicationController;
import com.example.talent_api.controller.JobController;
import com.example.talent_api.controller.UserController;
import com.example.talent_api.model.Application;
import com.example.talent_api.model.Job;
import com.example.talent_api.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerIntegrationTests {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationController applicationController;

     @Autowired
    private UserController userController;

    @Autowired
    private JobController jobController;

    // Sample Data
    private final User userOne = new User("user1", "pass1", "Candidate");
    private final User userTwo = new User("user2", "pass2", "Manager");
    private final Job jobOne = new Job(null, "Software Engineer", "Develop software", "Active", null, null, null);
    private final Job jobTwo = new Job(null, "Product Manager", "Manage product lifecycle", "Active", null, null, null);
    private final Application applicationOne = new Application(userOne, jobOne, "Sample Cover Letter 1", "Sample Resume 1", "Pending");
    private final Application applicationTwo = new Application(userTwo, jobTwo, "Sample Cover Letter 2", "Sample Resume 2", "Accepted");

    @BeforeEach
    public void beforeEach() {
        // Add users and jobs before adding applications
        this.userController.addUser(userOne);
        this.userController.addUser(userTwo);
        this.jobController.addJob(jobOne);
        this.jobController.addJob(jobTwo);
        this.applicationController.addApplication(applicationOne);
        this.applicationController.addApplication(applicationTwo);
    }

    @AfterEach
    public void afterEach() {
        // Clean up applications
        for (Application application : this.applicationController.getAllApplications()) {
            if (application.getId() > 2L) { // Adjust ID check based on your database state
                this.applicationController.deleteApplication(application.getId());
            }
        }

        // Clean up users and jobs
        for (User user : this.userController.getAllUsers()) {
            if (user.getId() > 2L) {
                this.userController.deleteUser(user.getId());
            }
        }
        for (Job job : this.jobController.getAlljobs()) {
            if (job.getId() > 2L) {
                this.jobController.deleteJob(job.getId());
            }
        }
    }

    @Test
    void applicationControllerLoads() {
        assertThat(applicationController).isNotNull();
    }

    @Test
    void testGetApplicationById() throws Exception {
        String endpointUrl = "http://localhost:" + port + "/applications";
        Application result = this.restTemplate.getForObject(endpointUrl + "/1", Application.class);

        assertThat(result).isNotNull();
        assertThat(result.getCover_letter()).contains("Updated Cover Letter");
    }

    @Test
    void testGetAllApplications() throws Exception {
        String endpointUrl = "http://localhost:" + port + "/applications";
        Application[] applications = this.restTemplate.getForObject(endpointUrl, Application[].class);
        System.out.println("Number of applications: " + applications.length);

        assertThat(applications.length).isGreaterThan(0);
        assertThat(applications.length).isEqualTo(4); // Adjust based on initial state
    }

   //below
     @Test
    void testAddApplication() throws Exception {
        // Remove application added in beforeEach
        String delUrl = "http://localhost:" + port + "/applications/" + this.applicationOne.getId();
        this.restTemplate.delete(delUrl);

        // Query all applications
        String getAllUrl = "http://localhost:" + port + "/applications";
        Application[] applications = this.restTemplate.getForObject(getAllUrl, Application[].class);

        // Create the request body with the headers
        String addUrl = getAllUrl + "/";

        HttpHeaders hp = new HttpHeaders();
        HttpEntity<Application> reqBodyWithHeaders = new HttpEntity<>(this.applicationOne, hp);
        ResponseEntity<Application> respApplication =
                this.restTemplate.postForEntity(addUrl, reqBodyWithHeaders, Application.class);

        // Query all applications again
        Application[] listPostAdd = this.restTemplate.getForEntity(getAllUrl, Application[].class).getBody();

        assertThat(applications).isNotNull();
        assertThat(applications.length).isEqualTo(3); // Adjust based on initial state

        assertThat(respApplication.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));

        assertThat(listPostAdd).isNotNull();
        assertThat(listPostAdd.length).isEqualTo(3); // Adjust based on initial state
    } 
        //above

    @Test
    void testUpdateApplication() throws Exception {
        // Get applicationOne
        String getAppOneUrl = "http://localhost:" + port + "/applications/" + this.applicationOne.getId();
        Application application = this.restTemplate.getForObject(getAppOneUrl, Application.class);

        // Create update URL
        String urlUpdate = getAppOneUrl;

        // Change a value in the application
        application.setCover_letter("Updated Cover Letter");

        // Update the value
        HttpHeaders hp = new HttpHeaders();
        HttpEntity<Application> reqBodyWithHeaders = new HttpEntity<>(application, hp);
        this.restTemplate.put(urlUpdate, reqBodyWithHeaders, Application.class);

        Application getUpdApplication = this.restTemplate.getForObject(getAppOneUrl, Application.class);

        // Check if successful
        assertThat(getUpdApplication).isNotNull();
        assertThat(getUpdApplication.getId()).isEqualTo(application.getId());
        assertThat(getUpdApplication.getCover_letter()).isEqualTo("Updated Cover Letter");
        assertThat(getUpdApplication.getCustom_resume()).isEqualTo(application.getCustom_resume());
        assertThat(getUpdApplication.getApplication_status()).isEqualTo(application.getApplication_status());
    }

    @Test
    void testDeleteApplication() {
        // Query all applications
        String getAllUrl = "http://localhost:" + port + "/applications";
        Application[] applications = this.restTemplate.getForObject(getAllUrl, Application[].class);

        // Get the added application
        String endpointUrl = "http://localhost:" + port + "/applications/";
        Application result = this.restTemplate.getForObject(endpointUrl + this.applicationOne.getId(), Application.class);

        // Delete one of the added applications
        String deleteUrl = "http://localhost:" + port + "/applications/" + result.getId();
        this.restTemplate.delete(deleteUrl);
        Application deletedApplication = this.restTemplate.getForObject(deleteUrl, Application.class);

        // Query all applications again
        Application[] listPostDelete = this.restTemplate.getForEntity(getAllUrl, Application[].class).getBody();

        assertThat(applications).isNotNull();
        assertThat(applications.length).isEqualTo(4); // Adjust based on initial state

        assertThat(listPostDelete).isNotNull();
        assertThat(listPostDelete.length).isEqualTo(3); // Adjust based on initial state
        assertThat(deletedApplication).isNull();
    }
}


