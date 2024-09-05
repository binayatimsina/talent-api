package com.example.talent_api.controller_tests;
import java.util.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.example.talent_api.controller.CandidateController;
import com.example.talent_api.model.Candidate;
import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;
import com.example.talent_api.service.CandidateService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CandidateControllerTests {

    @Mock
    private CandidateService cs;

    @InjectMocks
    private CandidateController cc;

    @Test
    void testGetAllCandidates(){
        //given
        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");
        Candidate c1 = new Candidate();
        Candidate c2 = new Candidate();

        given(cs.getAllCandidates()).willReturn(List.of(c1,c2));
        var cList = cc.getAllCandidates();
        //then
        assertThat(cList).isNotNull();
        assertThat(cList.getBody().size()).isEqualTo(2);
    }

    @Test
    void testGetCandidateById(){
        
        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");
        User u3 = new User("user3","pass3","regular");
        Candidate c1 = new Candidate(u1, "User One", "uo@abc.com", "user one address", "0000000000", "user1resume.pdf");
        Candidate c2 = new Candidate(u2,"User Two", "utwo@abc.com", "user two address", "0000000001", "user2resume.pdf");
        Candidate c3 = new Candidate(u3,"User Three", "uthree@abc.com", "user three address", "0000000002", "user3resume.pdf");

        //when
        given(cs.getCandidateById(2l)).willReturn(Optional.ofNullable(c2));
        //the return is Optional<Customer> so it should be nullable in case null returns
        var candidate = cc.getCandidateById(2l);
        //then
        assertThat(candidate.getBody()).isNotNull();
        assertThat(candidate.getBody()).isEqualTo(Optional.ofNullable(c2));
    }

    @Test
    void testAddCandidate(){
        User u1 = new User("user1","pass1","admin");
        Candidate c1 = new Candidate(u1, "User One", "uo@abc.com", "user one address", "0000000000", "user1resume.pdf");

        //when
        //the repository method and what it should return
        given(cs.addCandidate(c1)).willReturn(c1);
        //the return is Optional<Customer> so it should be nullable in case null returns
        var candidate = cc.addCandidate(c1); //the service method calling the repo...
        //then
        var c = (ResponseEntity<Candidate>) candidate;
        assertThat(candidate).isNotNull();

        assertThat(c.getBody()).isEqualTo(c1); //comparing service return vals with repo return val

    }

    @Test
    void testUpdateCandidate(){
        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");
        User u3 = new User("user3","pass3","regular");
        Candidate c1 = new Candidate(u1, "User One", "uo@abc.com", "user one address", "0000000000", "user1resume.pdf");
        Candidate c2 = new Candidate(u2,"User Two", "utwo@abc.com", "user two address", "0000000001", "user2resume.pdf");
        Candidate c3 = new Candidate(u3,"User Three", "uthree@abc.com", "user three address", "0000000002", "user3resume.pdf");

        Candidate uc = new Candidate(u3,"User Three Updated Name", "uthree@abc.com", "user three address", "0000000002", "user3resume.pdf");
        //when
        given(cs.updateCandidate(2l, uc)).willReturn(uc);
        var updCand = cc.updateCandidate(2l, uc);
        var c = (ResponseEntity<Candidate>) updCand;
        assertThat(updCand).isNotNull();
        assertThat(c.getBody().getFull_name()).isEqualTo("User Three Updated Name");
        assertThat(c.getBody()).isEqualTo(uc);

    }

    void testDeleteCandidate(){
        User u1 = new User("user1","pass1","admin");
        Candidate c1 = new Candidate();
        c1.setUser(u1);
        c1.setFull_name("Updated Name");
        c1.setEmail("updated@example.com");
        c1.setPhone("321-654-9870");
        c1.setResume("resume.pdf");
        //us.addUser(u1);
        cs.addCandidate(c1);

        //when
        //the repository method and what it should return
        given(cs.deleteCandidate(c1.getId())).willReturn(true);
        //the return is Optional<Customer> so it should be nullable in case null returns
        //the service method calling the repo...
        var resp = cc.deleteCandidate(c1.getId());
        //then
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody()).isEqualTo(true); //comparing service return vals with repo return val

    }

    void testGetCandidatesBySearchTerm() {

        User u1 = new User("user1","pass1","admin");
        User u2 = new User("user2","pass2","admin");
        Candidate c1 = new Candidate();
        Candidate c2 = new Candidate();

        given(cs.searchCandidates("user1")).willReturn(List.of(c1));
        var cList = cc.getCandidatesBySearchTerm("user1");
        //then
        assertThat(cList).isNotNull();
        assertThat(cList.getBody().size()).isEqualTo(1);

    }

    @AfterAll
    public static void cleanup(){
        System.out.println("tests finished running. inside reset function");
    }

    //deleteCandidate is not tested because it is not returning anything!
}
