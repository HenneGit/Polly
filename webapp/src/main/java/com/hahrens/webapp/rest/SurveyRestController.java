package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class SurveyRestController {


    private final SurveyService surveyService;

    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/surveys")
    public ResponseEntity<Collection<SurveyDTO>> getSurveys() {
        return ResponseEntity.ok(surveyService.findAll());
    }

    @PostMapping("/createSurvey")
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTO surveyDTO) {
        return ResponseEntity.ok(surveyService.create(surveyDTO));
    }

    @DeleteMapping("/deleteSurvey")
    public ResponseEntity<String> deleteSurvey(@RequestBody SurveyDTO surveyDTO) {
        surveyService.remove(surveyDTO);
        return ResponseEntity.ok("deleted");
    }

    @PutMapping("/updateSurvey")
    public ResponseEntity<SurveyDTO> updateSurvey(@RequestBody SurveyDTO surveyDTO) {
        return ResponseEntity.ok(surveyService.update(surveyDTO));
    }
}

