package com.example.talent_api.controller_tests;

import java.util.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.talent_api.controller.UserController;
import com.example.talent_api.model.User;
import com.example.talent_api.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    
    @Mock
    private UserService us;

    @InjectMocks
    private UserController uc;

    @Test
    void testGetAllUsers(){
        //given
        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");

        given(us.getAllUsers()).willReturn(List.of(u1,u2));
        var uList = uc.getAllUsers();
        List<User> list = uList.getBody();
        //then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void testGetUserById(){
        
        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");
        User u3 = new User("user3","pass3","regular");
        //when
        given(us.getUserById(2l)).willReturn(Optional.ofNullable(u2)); 
        //the return is Optional<Customer> so it should be nullable in case null returns
        var user = uc.getUserById(2l);
        //then
        assertThat(user.getBody()).isNotNull();
        assertThat(user.getBody()).isEqualTo(Optional.ofNullable(u2));
    }

    @Test
    void testAddUser(){
        User u1 = new User("user1","pass1","admin");
        
        //when
        given(us.addUser(u1)).willReturn(u1); //the repository method and what it should return

        //the return is Optional<Customer> so it should be nullable in case null returns
        var u = uc.addUser(u1); //the service method calling the repo...
        //then
        assertThat(u.getBody()).isNotNull();
        assertThat(u.getBody()).isEqualTo(u1); //comparing service return vals with repo return val

    }

    @Test
    void testUpdateUser(){
        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");
        User u3 = new User("user3","pass3","regular");

        User uu = new User("user2", "newPass2","admin");
        //when
        given(us.updateUser(2l, uu)).willReturn(uu);
        var u = uc.updateUser(2l, uu);
        User respUser = (User) u.getBody();

        assertThat(respUser).isNotNull();
        assertThat((respUser).getPassword()).isEqualTo("newPass2");
        assertThat(respUser).isEqualTo(uu);

    }

    @Test
    void testDeleteUser(){
        User u1 = new User("user1","pass1","admin");
        us.addUser(u1);
        //when
        given(us.deleteUser(u1.getId())).willReturn(true); //the repository method and what it should return

        //the return is Optional<Customer> so it should be nullable in case null returns
        var bool = uc.deleteUser(u1.getId()); //the service method calling the repo...
        //then
        assertThat(bool.getBody()).isNotNull();
        assertThat(bool.getBody()).isEqualTo(true); //comparing service return vals with repo return val

    }


    @AfterAll
    public static void cleanup(){
        System.out.println("tests finished running. inside reset function");
    }

    //deleteUser cannot be tested because it is not returning anything!

    


}
