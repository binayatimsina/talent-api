package com.example.talent_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.talent_api.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{
    @Query(value= "SELECT * from job where listing_status = 'Open'", nativeQuery=true)
    List<Job> findOpenJobs();

    @Query(value = "SELECT * from job where listing_status='Open' and manager_id= ?1", nativeQuery=true)
    List<Job> getOpenJobsByManager(long manager_id);

    @Query(value = "SELECT * from job where lower(listing_title) like %:searchTerm% or lower(job_description) like %:searchTerm% or lower(additional_information) like %:searchTerm%", nativeQuery = true)
    List<Job> searchFromJobs(@Param("searchTerm") String searchTerm);
    

}
