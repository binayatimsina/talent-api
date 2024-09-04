package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.talent_api.model.Candidate;
import com.example.talent_api.repository.CandidateRepository;


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

    public boolean deleteCandidate(Long id) {
        if(candidateRepository.existsById(id)){
            candidateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Candidate> searchCandidates (String searchTerm){
        return candidateRepository.searchCandidates(searchTerm);
    }

    public Candidate findCandidateByUsername(String username) {
        return candidateRepository.findByUsername(username);
    }
}
