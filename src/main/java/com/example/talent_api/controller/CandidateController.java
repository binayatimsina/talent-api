package com.example.talent_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
