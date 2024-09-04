package com.example.talent_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.talent_api.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>{

    @Query("SELECT c FROM Candidate c WHERE " +
        "LOWER(c.full_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
        "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
        "LOWER(c.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
        "LOWER(c.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " + 
        "LOWER(c.resume) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
    )
    List<Candidate> searchCandidates (@Param("searchTerm") String searchTerm);
}
