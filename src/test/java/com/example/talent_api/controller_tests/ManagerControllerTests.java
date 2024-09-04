package com.example.talent_api.controller_tests;
import com.example.talent_api.controller.ManagerController;
import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;
import com.example.talent_api.service.ManagerService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerControllerTests {

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerController managerController;

    @Test
    void contextLoads() {
        assertThat(managerController).isNotNull();
    }

    @Test
    void testGetAllManagers() {
        // Creating sample Manager objects
        User user = new User();
        user.setId(1L);

        Manager manager1 = new Manager();
        manager1.setUser(user);
        manager1.setFull_name("John Doe");
        manager1.setEmail("john.doe@example.com");
        manager1.setDepartment("HR");
        manager1.setPhone("123-456-7890");

        Manager manager2 = new Manager();
        manager2.setUser(user);
        manager2.setFull_name("Jane Smith");
        manager2.setEmail("jane.smith@example.com");
        manager2.setDepartment("IT");
        manager2.setPhone("987-654-3210");

        // Mocking service response
        when(managerService.getAllManagers()).thenReturn(Arrays.asList(manager1, manager2));

        // Calling the controller method
        var response = managerController.getAllManagers();
        List<Manager> managers = response.getBody();

        // Verifying the response
        assertThat(managers).isNotEmpty();
        assertThat(managers.size()).isEqualTo(2);
        assertThat(managers.get(0).getFull_name()).isEqualTo("John Doe");
        assertThat(managers.get(1).getFull_name()).isEqualTo("Jane Smith");
    }

    @Test
    void testGetManagerById() {
        // Creating a sample Manager object
        User user = new User();
        user.setId(1L);

        Manager manager = new Manager();
        manager.setUser(user);
        manager.setFull_name("John Doe");
        manager.setEmail("john.doe@example.com");
        manager.setDepartment("HR");
        manager.setPhone("123-456-7890");

        // Mocking service response
        when(managerService.getManagerById(1L)).thenReturn(Optional.of(manager));

        // Calling the controller method
        var response = managerController.getManagerById(1L);
        Optional<Manager> managerResp = response.getBody();
        // Verifying the response
        assertThat(managerResp).isPresent();
        assertThat(managerResp.get().getFull_name()).isEqualTo("John Doe");
    }

    @Test
    void testAddManager() {
        // Creating a sample Manager object
        User user = new User();
        user.setId(1L);

        Manager newManager = new Manager();
        newManager.setUser(user);
        newManager.setFull_name("Jane Doe");
        newManager.setEmail("jane.doe@example.com");
        newManager.setDepartment("Sales");
        newManager.setPhone("123-456-7890");

        // Mocking service response
        when(managerService.addManager(any(Manager.class))).thenReturn(newManager);

        // Calling the controller method
        var response = managerController.addManager(newManager);
        //Optional<Manager> managers = (Optional<Manager>) response.getBody();

        // Verifying the response
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(202));
        //assertThat(((Manager) managers).getFull_name()).isEqualTo("Jane Doe");
    }

    @Test
    void testUpdateManager() {
        // Creating a sample Manager object
        User user = new User();
        user.setId(1L);

        Manager updatedManager = new Manager();
        updatedManager.setUser(user);
        updatedManager.setFull_name("Updated Name");
        updatedManager.setEmail("updated@example.com");
        updatedManager.setDepartment("Finance");
        updatedManager.setPhone("321-654-9870");

        // Mocking service response
        when(managerService.updateManager(eq(1L), any(Manager.class))).thenReturn(updatedManager);

        // Calling the controller method
        var response = managerController.updateManager(1L, updatedManager);
        //Optional<Manager> managers = (Optional<Manager>) response.getBody();

        // Verifying the response
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(202));

        //assertThat(((Manager) response).getFull_name()).isEqualTo("Updated Name");
    }

    @Test
    void testDeleteManager(){
        User u1 = new User("user1","pass1","admin");
        Manager m1 = new Manager();
        m1.setUser(u1);
        m1.setFull_name("Updated Name");
        m1.setEmail("updated@example.com");
        m1.setDepartment("Finance");
        m1.setPhone("321-654-9870");
        //us.addUser(u1);
        managerService.addManager(m1);
        //when
        //the repository method and what it should return
        given(managerService.deleteManager(m1.getId())).willReturn(true);
        //the return is Optional<Customer> so it should be nullable in case null returns
        //the service method calling the repo...
        var resp = managerController.deleteManager(m1.getId());
        //then
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody()).isEqualTo(true); //comparing service return vals with repo return val

    }
     @AfterAll
     public static void cleanup() {
        System.out.println("tests finished running.inside reset function");
     }
}
