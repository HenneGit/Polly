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
        Collection<SurveyDTO> all = surveyService.findAll();
        if (all == null || all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    /**
     * find survey by id.
     * @return the survey found for that id.
     */
    @RequestMapping(value = "/{surveyId}", method = RequestMethod.GET)
    public ResponseEntity<SurveyDTO> getSurveyById(@PathVariable String surveyId)    {
        if (surveyId == null) {
            return ResponseEntity.badRequest().build();
        }
        SurveyDTO byId = surveyService.findById(surveyId);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId);
    }

    /**
     * create a given survey.
     * @param surveyDTO the survey to create.
     * @return the created survey having a pk.
     */
    @PostMapping("/add")
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        SurveyDTO createdSurvey = surveyService.create(surveyDTO);
        if (createdSurvey == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(null).body(createdSurvey);
    }

    /**
     * delete a given survey.
     * @param surveyId the surveyId to delete.
     */
    @RequestMapping(value = "/delete/{surveyId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSurvey(@PathVariable String surveyId) {
        //todo add pk not found exception.
        surveyService.deleteById(surveyId);
        return ResponseEntity.noContent().build();
    }

    /**
     * update the given survey.
     * @param surveyDTO the survey to update.
     * @return the updated survey.
     */
    @PutMapping("/update")
    public ResponseEntity<SurveyDTO> updateSurvey(@RequestBody SurveyDTOImpl surveyDTO) {
        //todo add pk not found exception.
        SurveyDTO update = surveyService.update(surveyDTO);
        if (update == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(update);
    }
}

