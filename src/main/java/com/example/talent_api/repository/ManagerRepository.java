package com.example.talent_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.talent_api.model.Manager;
import com.example.talent_api.model.User;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{
    // @Query(value = "SELECT * " +
    //            "FROM manager AS m " +
    //            "JOIN user AS u ON u.id = m.user_id " +
    //            "WHERE u.username = %:username%")
    @Query("SELECT m from Manager m JOIN m.user u where u.username=:username")
    Manager findManagerByUsername(@Param("username") String username);


    
}
