package com.example.talent_api.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Candidate>> getAllCandidates(){
        return ResponseEntity.status(HttpStatus.OK).body(candidateService.getAllCandidates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Candidate>> getCandidateById(@PathVariable("id") Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        if (candidate.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(candidate);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
        }
    }

    @PostMapping("/")
    public Object addCandidate(@RequestBody Candidate candidate) {
        try{
            Candidate savedCandidate = (Candidate) candidateService.addCandidate(candidate);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidate);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Object updateCandidate(@PathVariable("id") Long id, @RequestBody Candidate candidate) {
        try{
            Candidate updatedCandidate = candidateService.updateCandidate(id, candidate);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCandidate);
        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Integrity Violation Exception ");
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCandidate(@PathVariable("id") Long id) {
        if(candidateService.deleteCandidate(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Candidate>> getCandidatesBySearchTerm(@RequestParam String searchTerm){
        List<Candidate> candidates = candidateService.searchCandidates(searchTerm);
        return ResponseEntity.status(HttpStatus.OK).body(candidates);
    }

    @GetMapping("/getcandidate/{userid}")
    public ResponseEntity<Candidate> getCandidateByUserId(@PathVariable("userid") Long userid){
        Candidate candidate = candidateService.getCandidateByUserId(userid);
        return ResponseEntity.status(HttpStatus.OK).body(candidate);
    }
    
}
