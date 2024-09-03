package com.example.talent_api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.talent_api.controller.ApplicationController;
import com.example.talent_api.service.ApplicationService;

@SpringBootTest(classes = com.example.talent_api.controller.ApplicationController.class)
public class ApplicationControllerTests {
    
    @Autowired
	private ApplicationController applicationController;	
	
	@Test
	void contextLoads() {
		assertThat(applicationController).isNotNull();
        //assertThat(as).isNotNull();
	}	

    @Test
    void testGetAllApplications(){
        var apps = applicationController.getAllApplications();
        //then
        assertThat(apps).isNotNull();
    }

}
