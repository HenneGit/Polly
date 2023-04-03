package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * rest controller for delivering surveys.
 */
@RestController
@RequestMapping("/survey")
public class SurveyRestController {


    private final SurveyService surveyService;

    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * get all surveys.
     * @return all surveys found.
     */
    @GetMapping("/get")
    public ResponseEntity<Collection<SurveyDTO>> getSurveys() {
        return ResponseEntity.ok(surveyService.findAll());
    }

    /**
     * create a given survey.
     * @param surveyDTO the survey to create.
     * @return the created survey having a pk.
     */
    @PostMapping("/add")
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        return ResponseEntity.ok(surveyService.create(surveyDTO));
    }

    /**
     * delete a given survey.
     * @param surveyDTO the survey to delete.
     * @return confirmation that survey has been deleted.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        surveyService.remove(surveyDTO);
        return ResponseEntity.ok("deleted");
    }

    /**
     * update the given survey.
     * @param surveyDTO the survey to update.
     * @return the updated survey.
     */
    @PutMapping("/update")
    public ResponseEntity<SurveyDTO> updateSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        return ResponseEntity.ok(surveyService.update(surveyDTO));
    }
}

