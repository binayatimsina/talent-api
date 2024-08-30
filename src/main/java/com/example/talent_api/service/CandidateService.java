package com.example.talent_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.model.Candidate;
import com.example.talent_api.repository.CandidateRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository; 
    
    public  List<Candidate> getAllCandidates(){
        return candidateRepository.findAll();
    }
}
