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

import com.example.talent_api.controller.ManagerController;
import com.example.talent_api.model.Manager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManagerControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    private ManagerController managerController;

    private final Manager managerOne = new Manager(null, "manager1", "manager1@example.com", null, null);
    private final Manager managerTwo = new Manager(null, "manager2", "manager2@example.com", null, null);

    @BeforeEach
    public void beforeEach() {
        // Adding initial managers for each test
        this.managerController.addManager(this.managerOne);
        this.managerController.addManager(this.managerTwo);
    }

    @AfterEach
    public void afterEach() {
        // Cleanup managers after each test
        for (Manager manager : this.managerController.getAllManagers()) {
            if (manager.getId() > 0) {
                this.managerController.deleteManager(manager.getId());
                
                String deleteUrl = "http://localhost:" + port + "/managers/" + manager.getId();
        
                this.restTemplate.delete(deleteUrl);
            }
        }
    }

    @Test
    void managerControllerLoads() {
        assertThat(managerController).isNotNull();
    }

    @Test
    void testGetManagerById() throws Exception {
        // Test case for getting a manager by ID
        String endpointUrl = "http://localhost:" + port + "/managers/" + managerOne.getId();
        Manager result = this.restTemplate.getForObject(endpointUrl, Manager.class);

        System.out.println("result-2: " + result.getFull_name());

        assertThat(result.getFull_name()).contains("manager1");
    }

    @Test
    void testGetAllManagers() throws Exception {
        // Test case for getting all managers
        String endpointUrl = "http://localhost:" + port + "/managers";
        Manager[] managers = this.restTemplate.getForObject(endpointUrl, Manager[].class);

        System.out.println("number of managers: " + managers.length);

        assertThat(managers).isNotNull();
        assertThat(managers.length).isGreaterThan(0);
    }

   //below
    @Test
    void testAddManager() throws Exception {
        // Test case for adding a new manager
        String uriAdd = "http://localhost:" + port + "/managers";
        HttpHeaders headers = new HttpHeaders();
       

        Manager newManager = new Manager(null, "newManager", "new.manager@example.com", uriAdd, uriAdd);
        HttpEntity<Manager> request = new HttpEntity<>(newManager, headers);
        ResponseEntity<Manager> response = this.restTemplate.postForEntity(uriAdd, request, Manager.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(405);
        assertThat(response.getBody().getFull_name()).isEqualTo(null);
    }
    //above

    @Test
    void testUpdateManager() throws Exception {
        // Test case for updating an existing manager
        String uriUpdate = "http://localhost:" + port + "/managers/" + managerOne.getId();
        HttpHeaders headers = new HttpHeaders();
        

        managerOne.setFull_name("Updated Manager");
        HttpEntity<Manager> request = new HttpEntity<>(managerOne, headers);
        ResponseEntity<Manager> response = this.restTemplate.exchange(uriUpdate, HttpMethod.PUT, request, Manager.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getFull_name()).isEqualTo("Updated Manager");
    }

   
     @Test
    void testDeleteManager(){
        
        //get added manager
        String endpointUrl = "http://localhost:" + port + "/managers/";
        Manager result = this.restTemplate.getForObject(endpointUrl+this.managerOne.getId(), Manager.class );

        //delete one of the added users
        String deleteUrl = "http://localhost:" + port + "/managers/" + result.getId();
        
        this.restTemplate.delete(deleteUrl);
        Manager deletedManager = this.restTemplate.getForObject(deleteUrl, Manager.class);
        
        assertThat(deletedManager).isNull();
        
    }
    
}

    
