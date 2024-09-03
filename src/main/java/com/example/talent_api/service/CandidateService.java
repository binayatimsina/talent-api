package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.CandidateRepository;
import com.example.talent_api.model.Candidate;


@Service
public class CandidateService {
    
    @Autowired
    private CandidateRepository candidateRepository;

    public Object addCandidate(Candidate candidate) {
        
        return (candidateRepository.save(candidate)); 
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate currentCandidate =  (Candidate) candidateRepository.findById(id).get();
        currentCandidate.setUser(candidate.getUser());
        currentCandidate.setFull_name(candidate.getFull_name());
        currentCandidate.setAddress(candidate.getAddress());
        currentCandidate.setEmail(candidate.getEmail());
        currentCandidate.setPhone(candidate.getPhone());
        currentCandidate.setResume(candidate.getResume());
        candidateRepository.save(currentCandidate);
        return currentCandidate;

    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
