package com.example.talent_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.talent_api.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{

}
