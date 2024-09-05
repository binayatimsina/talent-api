package com.example.talent_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.talent_api.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{
    @Query("SELECT m FROM Manager m JOIN m.user u WHERE u.username = :username")
    Manager findByUsername(@Param("username") String username);
}
