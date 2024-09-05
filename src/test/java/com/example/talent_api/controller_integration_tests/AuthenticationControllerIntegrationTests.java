package com.example.talent_api.controller_integration_tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.talent_api.controller.UserController;
import com.example.talent_api.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserController userController;

    // Sample user data
    private final User sampleUser = new User("testuser", "testpass", "Candidate");

    @BeforeEach
    public void setUp() {
        // Register the sample user before each test
        String registerUrl = "http://localhost:" + port + "/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(sampleUser, headers);
        restTemplate.postForEntity(registerUrl, request, String.class);


    }

    @AfterEach
    public void tearDown() {
        ResponseEntity<List<User>> resp = this.userController.getAllUsers();
        List<User> users = resp.getBody();
        for (User user : users) {
            if(user.getId() > 9l){
                this.userController.deleteUser(user.getId());

                String deleteUrl = "http://localhost:" + port + "/users/" + user.getId();
        
                this.restTemplate.delete(deleteUrl);
            }
        }
    }

    @Test
    void contextLoads() {
        // Ensure the application context loads correctly
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void testRegistrationSuccess() {
        // Test successful registration of a new user
        String url = "http://localhost:" + port + "/registration";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User newUser = new User("newuser", "newpass", "Candidate");

        HttpEntity<User> request = new HttpEntity<User>(newUser, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        //assertThat(response.getBody()).contains("username already exists");
    }

    @Test
    void testRegistrationFailure() {
        // Test registration failure due to duplicate username
        String url = "http://localhost:" + port + "/registration";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User newUser = new User("michael", "newpass", "Candidate");
        HttpEntity<User> request = new HttpEntity<>(newUser, headers); // Using the already registered user

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("username already exists");
    }

    @Test
    void testLoginSuccess() {
        // Test successful login
        String url = "http://localhost:" + port + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User loginUser = new User("michael", "michael", "Hiring_Manager");
        HttpEntity<User> request = new HttpEntity<>(loginUser, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
       String responseBody = response.getBody();
       assertThat(responseBody).isNotNull();
        
    }
 
    @Test
    void testLoginFailure() {
        // Test login failure due to incorrect credentials
        String url = "http://localhost:" + port + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User wrongUser = new User("michael", "michael2", "Hiring_Manager");
        HttpEntity<User> request = new HttpEntity<>(wrongUser, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isNull();;
    }
}

