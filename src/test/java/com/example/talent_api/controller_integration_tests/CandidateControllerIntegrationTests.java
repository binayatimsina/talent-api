

package com.example.talent_api.controller_integration_tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.annotations.CascadeType;
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

import com.example.talent_api.controller.CandidateController;
import com.example.talent_api.controller.UserController;
import com.example.talent_api.model.Candidate;
import com.example.talent_api.model.User;

import jakarta.persistence.OneToOne;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CandidateControllerIntegrationTests {
    @LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CandidateController candidateController;	

    @Autowired
    private UserController userController;

    private final User userOne = new User("user1","pass1","Candidate");
    private final User userTwo = new User("user2","pass2","Manager");
    private final Candidate candidateOne = new Candidate(userOne, "User One", "uone@abc.com", "user one address", "0000000000", "user1resume.pdf");
    private final Candidate candidateTwo = new Candidate(userTwo,"User Two", "utwo@abc.com", "user two address", "0000000001", "user2resume.pdf");


    @BeforeEach
    public void beforeEach() {
        this.userController.addUser(userOne);
        this.userController.addUser(userTwo);
        this.candidateController.addCandidate(candidateOne);
        this.candidateController.addCandidate(candidateTwo);
    }

    @AfterEach
    public void afterEach() {
        for (Candidate candidate : this.candidateController.getAllCandidates()) {
            if(candidate.getId() > 5l){ //there are five original records from sql files
                this.candidateController.deleteCandidate(candidate.getId());
            }
        }
        for (User user : this.userController.getAllUsers()) {
            if(user.getId() > 9l){
                this.userController.deleteUser(user.getId());
            }
        }

    }

    @Test
	void candidateControllerLoads() {
		assertThat(candidateController).isNotNull();
	}	

    @Test
	void testGetCandidateById() throws Exception {

        String endpointUrl = "http://localhost:" + port + "/candidates";
        Candidate result = this.restTemplate.getForObject(endpointUrl+"/3", Candidate.class );
			
        assertThat(result).isNotNull();
		System.out.println("result-2: " + result.getFull_name());
		
		assertThat(result.getFull_name()).contains("John Doe");
	}
    
    @Test
    void testGetAllCandidates() throws Exception {
        String endpointUrl = "http://localhost:" + port + "/candidates";
        Candidate[] candidates = this.restTemplate.getForObject(endpointUrl, Candidate[].class); 
        System.out.println("number of candidates: " + candidates.length);
		
        assertThat(candidates.length).isGreaterThan(0);
		assertThat(candidates.length).isEqualTo(7);
    }



    @Test
    void testAddCandidate() throws Exception {
        //remove candidate added in before each
        String delUrl = "http://localhost:" + port + "/candidates/" + this.candidateOne.getId();
        this.restTemplate.delete(delUrl);

        //Query all candidates
        String getAllUrl = "http://localhost:" + port + "/candidates";
        Candidate[] candidates = this.restTemplate.getForObject(getAllUrl, Candidate[].class);

        //Create the request body with the headers
        String addUrl = getAllUrl + "/";

        HttpHeaders hp = new HttpHeaders();
        HttpEntity<Candidate> reqBodyWithHeaders = new HttpEntity<Candidate>(this.candidateOne, hp);
        ResponseEntity<Candidate> respCandidate =
                this.restTemplate.postForEntity(addUrl, reqBodyWithHeaders, Candidate.class);

        // Query all candidates again.
        Candidate[] listPostAdd = this.restTemplate.getForEntity(getAllUrl, Candidate[].class).getBody();

        assertThat(candidates).isNotNull();
        assertThat(candidates.length).isEqualTo(6);

        assertThat(respCandidate.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        assertThat(listPostAdd).isNotNull();
        assertThat(listPostAdd.length).isEqualTo(7);

    }

    @Test
    void testUpdateUser() throws Exception {
        //Get userOne
        String getCandOneUrl = "http://localhost:" + port + "/candidates/"+this.candidateOne.getId();
        Candidate candidate = this.restTemplate.getForObject(getCandOneUrl, Candidate.class);

        //Create update url
        String urlUpdate = getCandOneUrl;

        //change a value in user
        candidate.setFull_name("New Full Name");

        //update the value
        HttpHeaders hp = new HttpHeaders();
        HttpEntity<Candidate> reqBodyWithHeaders = new HttpEntity<>(candidate, hp);
        this.restTemplate.put(urlUpdate, reqBodyWithHeaders, Candidate.class);

        Candidate getUpdCandidate = this.restTemplate.getForObject(getCandOneUrl, Candidate.class);
        
        //check if successful
        assertThat(getUpdCandidate).isNotNull();
        assertThat(getUpdCandidate.getId()).isEqualTo(candidate.getId());
        assertThat(getUpdCandidate.getFull_name()).isEqualTo("New Full Name");
        assertThat(getUpdCandidate.getAddress()).isEqualTo(candidate.getAddress());
        assertThat(getUpdCandidate.getEmail()).isEqualTo(candidate.getEmail());

    }

    @Test
    void testDeleteCandidate(){

        //Query all candidates
        String getAllUrl = "http://localhost:" + port + "/candidates";
        Candidate[] candidates = this.restTemplate.getForObject(getAllUrl, Candidate[].class);

        //get added candidates
        String endpointUrl = "http://localhost:" + port + "/candidates/";
        Candidate result = this.restTemplate.getForObject(endpointUrl+this.candidateOne.getId(), Candidate.class );

        //delete one of the added candidates
        String deleteUrl = "http://localhost:" + port + "/candidates/" + result.getId();
        
        this.restTemplate.delete(deleteUrl);
        Candidate deletedCandidate = this.restTemplate.getForObject(deleteUrl, Candidate.class);
        
        // Query all candidates again.
        Candidate[] listPostDelete = this.restTemplate.getForEntity(getAllUrl, Candidate[].class).getBody();

        assertThat(candidates).isNotNull();
        assertThat(candidates.length).isEqualTo(7);

        assertThat(listPostDelete).isNotNull();
        assertThat(listPostDelete.length).isEqualTo(6);
        assertThat(deletedCandidate).isNull();
    }

}


