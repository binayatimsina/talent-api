package com.example.talent_api.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.talent_api.model.User;

@RestController
@RequestMapping("/users")
@Tag(name = "User APIs", description = "Operations related to users")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        if(userList.size()!=0){
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        Optional<User> userById = userService.getUserById(id);
        if(userById.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userById);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try{
            User savedUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Integrity Violation Exception ");
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
        }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try{
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Integrity Violation Exception ");
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        if(userService.deleteUser(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
