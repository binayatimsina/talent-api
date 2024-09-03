package com.example.talent_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

}
