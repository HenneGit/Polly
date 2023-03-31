package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/survey")
public class SurveyRestController {


    private final SurveyService surveyService;

    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<SurveyDTO>> getSurveys() {
        return ResponseEntity.ok(surveyService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        return ResponseEntity.ok(surveyService.create(surveyDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        surveyService.remove(surveyDTO);
        return ResponseEntity.ok("deleted");
    }

    @PutMapping("/update")
    public ResponseEntity<SurveyDTO> updateSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        return ResponseEntity.ok(surveyService.update(surveyDTO));
    }
}

