package com.example.talent_api.controller_integration_tests;

import com.example.talent_api.controller.JobController;
import com.example.talent_api.controller.UserController;

import com.example.talent_api.controller.ManagerController;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.talent_api.model.Job;
import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JobControllerIntegrationTests {
     private final User user = new User("julian", "password", "user");
     private final User user2 = new User("vand", "pass", "user");
     private final Manager manager = new Manager(user, "Julan S Vanderpool", "julianva09@gmail.com", "Tech", "7573777902");
     private final Manager manager2 = new Manager(user2, "Joseph D Smith", "josephd09@gmail.com", "Finance", "4045632388");
     private final Job job = new Job(manager, "Software", "Name", "Developer", "Description", "Five Years On Job", "Agency");
     private final Job job2 = new Job(manager2, "Finance", "Banker", "Senior Banker", "Description", "10 Years On Job", "Agency");
     @LocalServerPort
     private int port;

	@Autowired
	private TestRestTemplate restTemplate;
    @Autowired
	private JobController jobController;	
    @Autowired
    private ManagerController managerController;
    @Autowired
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        this.jobController.addJob(this.job);
        this.jobController.addJob(this.job2);
        this.managerController.addManager(this.manager);
        this.managerController.addManager(this.manager2);
        this.userController.addUser(this.user);
        this.userController.addUser(this.user2);
    }

    @AfterEach
    public void afterEach() {
        ResponseEntity<List<Job>> resp = this.jobController.getAlljobs();
        List<Job> jobs = resp.getBody();
        for (Job job : jobs) {
            String deleteUrl = "http://localhost:" + port + "/jobs/" + job.getId();
            if(job.getId() > 5l){
                this.jobController.deleteJob(job.getId());
            }
        }
        ResponseEntity<List<Manager>> resp2 = this.managerController.getAllManagers();
        List<Manager> managers = resp2.getBody();
        for(Manager manager : managers) {
            
            if(manager.getId() >3l) {
                String deleteUrl = "http://localhost:" + port + "/managers/" + manager.getId();
                this.restTemplate.delete(deleteUrl);
                this.managerController.deleteManager(manager.getId());
            }
        }
        ResponseEntity<List<User>> resp3 = this.userController.getAllUsers();
        List<User> users = resp3.getBody();
        for (User user : users) {
            
            if(user.getId() > 9l){ 
                String deleteUrl = "http://localhost:" + port + "/users/" + user.getId();
                this.restTemplate.delete(deleteUrl);
                this.userController.deleteUser(user.getId());
            }
        }
        
        
    }

    @Test
	void jobControllerLoads() {
		assertThat(jobController).isNotNull();
	}	

    @Test
	void testGetJobById() throws Exception {

        String endpointUrl = "http://localhost:" + port + "/jobs";
        Job result = this.restTemplate.getForObject(endpointUrl+"/2", Job.class);
		assertThat(result).isNotNull();
		System.out.println("result-2: " + result.getDepartment());
		
		assertThat(result.getDepartment()).contains("Software Development");
	}
    @Test
    void testGetAllUsers() throws Exception {
        String endpointUrl = "http://localhost:" + port + "/jobs";
        Job[] jobs = this.restTemplate.getForObject(endpointUrl, Job[].class); 
        System.out.println("number of jobs: " + jobs.length);
		
        assertThat(jobs.length).isGreaterThan(0);
		assertThat(jobs.length).isEqualTo(6);
    }



    @Test
    void testAddUser() throws Exception {
        //remove user added in before each
        String delUrl = "http://localhost:" + port + "/jobs/" + this.job.getId();
        this.restTemplate.delete(delUrl);

        //Query all users
        String getAllUrl = "http://localhost:" + port + "/jobs";
        Job[] jobs = this.restTemplate.getForObject(getAllUrl, Job[].class);

        //Create the request body with the headers
        String uriAdd = getAllUrl + "/";
        //HttpHeaders headersPost = ControllerTestsData.getDefaultPutOrPostMediaTypeHeaders();

        HttpHeaders hp = new HttpHeaders();
        HttpEntity<Job> reqBodyWithHeaders = new HttpEntity<>(this.job, hp);
        ResponseEntity<Job> respUser =
                this.restTemplate.postForEntity(uriAdd, reqBodyWithHeaders, Job.class);

        // Query all users again.
        Job[] listPostAdd = this.restTemplate.getForEntity(getAllUrl, Job[].class).getBody();

        assertThat(jobs).isNotNull();
        assertThat(jobs.length).isEqualTo(5);

        //assertThat(respUser.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(respUser.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));


        assertThat(listPostAdd).isNotNull();
        assertThat(listPostAdd.length).isEqualTo(6);

    }
    @Test
    void testGetOpenJobs() throws Exception{
        String getUrl = "http://localhost:" + port + "/open";
        Job[] openJobs = this.restTemplate.getForObject(getUrl, Job[].class);
        System.out.println("number of jobs: " + openJobs.length);

        assertThat(openJobs.length).isGreaterThan(0);
		assertThat(openJobs.length).isEqualTo(6);
    }
    @Test
    void getOpenJobsByManager()throws Exception {
        String getUrl = "http://localhost:" + port + "/open/manager";
        Job[] openJobsByManger = this.restTemplate.getForObject(getUrl + "/2", Job[].class);
        System.out.println("number of jobs by manager: " + openJobsByManger.length);

        assertThat(openJobsByManger.length).isGreaterThan(0);
		assertThat(openJobsByManger.length).isEqualTo(6);
    }
    @Test 
    void testSearch() throws Exception {
        String getUrl = "http://localhost:" + port + "/search";
        Job[] search = this.restTemplate.getForObject(getUrl + "?searchTerm=linda", Job[].class);
        System.out.println("number of matching candidates: " + search.length);
        assertThat(search.length).isGreaterThan(0);
		assertThat(search.length).isEqualTo(1);
    }
    @Test
    void testUpdateUser() throws Exception {
        //Get userOne
        String getUserOneUrl = "http://localhost:" + port + "/jobs/"+this.job.getId();
        Job job = this.restTemplate.getForObject(getUserOneUrl, Job.class);

        //Create update url
        String urlUpdate = getUserOneUrl;

        //change a value in user
        job.setDepartment("new_department");
        
        //update the value
        HttpHeaders hp = new HttpHeaders();
        HttpEntity<User> reqBodyWithHeaders = new HttpEntity<>(user, hp);
        this.restTemplate.put(urlUpdate, reqBodyWithHeaders, Job.class);

        Job getUpdJob = this.restTemplate.getForObject(getUserOneUrl, Job.class);
        
        //check is successful
        assertThat(getUpdJob).isNotNull();
        assertThat(getUpdJob.getId()).isEqualTo(job.getId());
        assertThat(getUpdJob.getDepartment()).isEqualTo("new_department");

    }

    @Test
    void testDeleteUser(){
        //this.restTemplate.delete("http://localhost:" + port + "/users/12");
        //get added user
        String endpointUrl = "http://localhost:" + port + "/jobs/";
        Job result = this.restTemplate.getForObject(endpointUrl+this.job.getId(), Job.class);

        //delete one of the added users
        String deleteUrl = "http://localhost:" + port + "/jobs/" + result.getId();
        
        this.restTemplate.delete(deleteUrl);
        Job deletedJob = this.restTemplate.getForObject(deleteUrl, Job.class);
        
        assertThat(deletedJob).isNull();
        
    }



}
