package com.example.talent_api.controller_integration_tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
        //this.restTemplate.delete("http://localhost:" + port + "/users/12");
        this.userController.addUser(this.userOne);
        this.userController.addUser(this.userTwo);
    }

    @AfterEach
    public void afterEach() {
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
	void userControllerLoads() {
		assertThat(userController).isNotNull();
	}	

    @Test
	void testGetUserById() throws Exception {

        String endpointUrl = "http://localhost:" + port + "/users";
        User result = this.restTemplate.getForObject(endpointUrl+"/2", User.class );
			
		System.out.println("result-2: " + result.getUsername());
		
		assertThat(result.getUsername()).contains("edsmith");
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

        //assertThat(respUser.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(respUser.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));


        assertThat(listPostAdd).isNotNull();
        assertThat(listPostAdd.length).isEqualTo(11);

    }

    @Test
    void testUpdateUser() throws Exception {
        //Get userOne
        String getUserOneUrl = "http://localhost:" + port + "/users/"+this.userOne.getId();
        User user = this.restTemplate.getForObject(getUserOneUrl, User.class);

        //Create update url
        String urlUpdate = getUserOneUrl;

        //change a value in user
        user.setUsername("new_username");

        //update the value
        HttpHeaders hp = new HttpHeaders();
        HttpEntity<User> reqBodyWithHeaders = new HttpEntity<>(user, hp);
        this.restTemplate.put(urlUpdate, reqBodyWithHeaders, User.class);

        User getUpdUser = this.restTemplate.getForObject(getUserOneUrl, User.class);
        
        //check is successful
        assertThat(getUpdUser).isNotNull();
        assertThat(getUpdUser.getId()).isEqualTo(user.getId());
        assertThat(getUpdUser.getUsername()).isEqualTo("new_username");
        assertThat(getUpdUser.getType()).isEqualTo(user.getType());
        assertThat(getUpdUser.getPassword()).isEqualTo(user.getPassword());

    }

    @Test
    void testDeleteUser(){
        //this.restTemplate.delete("http://localhost:" + port + "/users/12");
        //get added user
        String endpointUrl = "http://localhost:" + port + "/users/";
        User result = this.restTemplate.getForObject(endpointUrl+this.userOne.getId(), User.class );

        //delete one of the added users
        String deleteUrl = "http://localhost:" + port + "/users/" + result.getId();
        
        this.restTemplate.delete(deleteUrl);
        User deletedUser = this.restTemplate.getForObject(deleteUrl, User.class);
        
        assertThat(deletedUser).isNull();
        
    }

}


