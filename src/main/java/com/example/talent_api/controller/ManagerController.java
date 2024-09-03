package com.example.talent_api.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins="*")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    
    @GetMapping("")
    public List<Manager> getAllManagers(){
        return managerService.getAllManagers();
    }

    @GetMapping("/{id}")
    public Optional<Manager> getManagerById(@PathVariable("id") Long id) {
        return managerService.getManagerById(id);
    }

    @PostMapping("/")
    public Object addManager(@RequestBody Manager manager) {
        return managerService.addManager(manager);
    }

    @PutMapping("/{id}")
    public Object updateManager(@PathVariable("id") Long id, @RequestBody Manager manager) {
        return managerService.updateManager(id, manager);
    }

    @DeleteMapping("/{id}")
    public void deleteManager(@PathVariable("id") Long id) {
        managerService.deleteManager(id);
    }
    
}
