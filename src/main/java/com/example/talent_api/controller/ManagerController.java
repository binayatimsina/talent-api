package com.example.talent_api.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.model.Manager;
import com.example.talent_api.service.ManagerService;

@RestController
@RequestMapping("/managers")
@Tag(name = "Manager APIs", description = "Operations related to managers")
@CrossOrigin(origins="*")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    
    @GetMapping("")
    public ResponseEntity<List<Manager>> getAllManagers(){
        return ResponseEntity.status(HttpStatus.OK).body(managerService.getAllManagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Manager>> getManagerById(@PathVariable("id") Long id) {
        Optional<Manager> manager = managerService.getManagerById(id);
        if (manager.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(manager);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    }

    @PostMapping("/")
    public ResponseEntity<Object> addManager(@RequestBody Manager manager) {
        try{
            Object savedManager = managerService.addManager(manager);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedManager);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured" + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateManager(@PathVariable("id") Long id, @RequestBody Manager manager) {
        try {
            Manager updatedManager = managerService.updateManager(id, manager);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedManager);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured:" + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteManager(@PathVariable("id") Long id) {
        if (managerService.deleteManager(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    
}
