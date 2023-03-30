package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SurveyRestController {


    private final SurveyService surveyService;

    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    @GetMapping("/surveys")
    public ResponseEntity<Collection<SurveyDTO>> getAllTestEntity() {
        Collection<SurveyDTO> all = surveyService.findAll();
        surveyService.create(new SurveyDTOImpl(null, "Hello", "Descr", null));

        return ResponseEntity.ok(surveyService.findAll());
    }
}

