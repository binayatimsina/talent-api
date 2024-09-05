package com.example.talent_api.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.service.CandidateService;
import com.example.talent_api.service.ManagerService;
import com.example.talent_api.service.UserService;
import com.example.talent_api.model.Candidate;
import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;
import com.example.talent_api.security.JWTService;

import java.util.*;

@RestController
@CrossOrigin(origins="*")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> loginCredential) {
        String username = loginCredential.get("username");
        String password = loginCredential.get("password");
        User currentUser = userService.findUserByUsername(username, password);
        if (currentUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtService.generateToken(username));
            
            if (currentUser.getType().equals("Candidate")) {
                // Candidate candidateUser = null;
                Candidate candidateUser = candidateService.findCandidateByUsername(username);
                response.put("user", candidateUser);
                System.out.println(response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else if (currentUser.getType().equals("Hiring_Manager")) {
                response.put("user", managerService.findManagerByUsername(username));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response.put("user", currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody Map<String, String> registrationCredential) {
        String username = registrationCredential.get("username");
        String password = registrationCredential.get("password");
        String type = registrationCredential.get("type");
        
        User newUser = userService.register(username, password, type);
        if (type.equals("Hiring_Manager")) {
            String fullName = registrationCredential.get("full_name");
            String email = registrationCredential.get("email");
            String department = registrationCredential.get("department");
            String phone = registrationCredential.get("phone");
            managerService.addManager(new Manager(newUser,fullName, email, department, phone));
        } else if (type.equals("Candidate")){
            String fullName = registrationCredential.get("full_name");
            String email = registrationCredential.get("email");
            String address = registrationCredential.get("address");
            String phone = registrationCredential.get("phone");
            String resume = registrationCredential.get("resume");
            candidateService.addCandidate(new Candidate(newUser, fullName, email, address, phone, resume));
        }

        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exists.");
        }


    }

}
