package com.example.talent_api.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.model.Candidate;
import com.example.talent_api.service.CandidateService;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins="*")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    
    @GetMapping("")
    public List<Candidate> getAllCandidates(){
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public Optional<Candidate> getCandidateById(@PathVariable("id") Long id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping("/")
    public Object addCandidate(@RequestBody Candidate candidate) {
        return candidateService.addCandidate(candidate);
    }

    @PutMapping("/{id}")
    public Object updateCandidate(@PathVariable("id") Long id, @RequestBody Candidate candidate) {
        return candidateService.updateCandidate(id, candidate);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
    }

    @GetMapping("/search")
    public List<Candidate> getCandidatesBySearchTerm(@RequestParam String searchTerm){
        List<Candidate> candidates = candidateService.searchCandidates(searchTerm);
        return candidates;
    }
    
}
