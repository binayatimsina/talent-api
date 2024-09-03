package com.example.talent_api;

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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.example.talent_api.controller.UserController;
import com.example.talent_api.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests {
    @LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	
	@Autowired
	private UserController userController;	

    private final User userOne = new User("user1","pass1","Candidate");
    private final User userTwo = new User("user2","pass2","Manager");


    @BeforeEach
    public void beforeEach() {
        this.userController.addUser(this.userOne);
        this.userController.addUser(this.userTwo);
    }

    @AfterEach
    public void afterEach() {
        for (User user : this.userController.getAllUsers()) {
            this.userController.deleteUser(user.getId());
        }
    }

    @Test
	void userControllerLoads() {
		assertThat(userController).isNotNull();
	}	

    @Test
	void testGetUserById() throws Exception {

        String endpointUrl = "http://localhost:" + port + "/users";
        User result = this.restTemplate.getForObject(endpointUrl+"/1", User.class );
			
		System.out.println("result-2: " + result.getUsername());
		
		assertThat(result.getUsername()).contains("cindyloo");
	}
    
    @Test
    void testGetAllUsers() throws Exception {
        String endpointUrl = "http://localhost:" + port + "/users";
        User[] users = this.restTemplate.getForObject(endpointUrl, User[].class); 
        System.out.println("number of users: " + users.length);
		
        assertThat(users.length).isGreaterThan(0);
		assertThat(users.length).isEqualTo(11);
    }



    @Test
    void testAddUser() throws Exception {
        //remove user added in before each
        String delUrl = "http://localhost:" + port + "/users/" + this.userOne.getId();
        this.restTemplate.delete(delUrl);

        //Query all users
        String getAllUrl = "http://localhost:" + port + "/users";
        User[] users = this.restTemplate.getForObject(getAllUrl, User[].class);

        //Create the request body with the headers
        String uriAdd = getAllUrl + "/";
        //HttpHeaders headersPost = ControllerTestsData.getDefaultPutOrPostMediaTypeHeaders();

        HttpHeaders hp = new HttpHeaders();
        HttpEntity<User> reqBodyWithHeaders = new HttpEntity<>(this.userOne, hp);
        ResponseEntity<User> respUser =
                this.restTemplate.postForEntity(uriAdd, reqBodyWithHeaders, User.class);

        // Query all users again.
        User[] listPostAdd = this.restTemplate.getForEntity(getAllUrl, User[].class).getBody();

        assertThat(users).isNotNull();
        assertThat(users.length).isEqualTo(10);

        assertThat(respUser.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        assertThat(listPostAdd).isNotNull();
        assertThat(listPostAdd.length).isEqualTo(11);




    }

    @Test
    void testDeleteUser(){
        
        String deleteUrl = "http://localhost:" + port + "/users/1";
        
        this.restTemplate.delete(deleteUrl);
        User deletedUser = this.restTemplate.getForObject(deleteUrl, User.class);
        
        assertThat(deletedUser).isNull();
    }

    @Before(value = "testDeleteUser")
    void insertUserToDelete(){
        
    }
}


