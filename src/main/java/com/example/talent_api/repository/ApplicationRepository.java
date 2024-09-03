package com.example.talent_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.talent_api.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{

    List<Application> findByJob_Manager_Id(Long managerId);
    Optional<Application> findByJob_Id(Long jobId);

}
