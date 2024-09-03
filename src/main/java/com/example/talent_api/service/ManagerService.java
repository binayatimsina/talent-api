package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.repository.ManagerRepository;
import com.example.talent_api.model.Manager;


@Service
public class ManagerService {
    
    @Autowired
    private ManagerRepository managerRepository;

    public Object addManager(Manager manager) {
        
        return (managerRepository.save(manager)); 
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    public Manager updateManager(Long id, Manager manager) {
        Manager currentManager =  (Manager) managerRepository.findById(id).get();
        currentManager.setUser(manager.getUser());
        currentManager.setFull_name(manager.getFull_name());
        currentManager.setDepartment(manager.getDepartment());
        currentManager.setEmail(manager.getEmail());
        currentManager.setPhone(manager.getPhone());
        managerRepository.save(currentManager);
        return currentManager;

    }

    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
