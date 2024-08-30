package com.example.talent_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.talent_api.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{

}
