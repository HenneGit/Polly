package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class QuestionRestController {


    private final QuestionService questionService;

    public QuestionRestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public ResponseEntity<Collection<QuestionDTO>> getSurveys() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @PostMapping("/postQuestion")
    public ResponseEntity<QuestionDTO> createSurvey(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.create(questionDTO));
    }

    @DeleteMapping("/deleteQuestion")
    public ResponseEntity<String> deleteSurvey(@RequestBody QuestionDTO questionDTO) {
        questionService.remove(questionDTO);
        return ResponseEntity.ok("deleted");
    }

    @PutMapping("updateQuestion")
    public ResponseEntity<QuestionDTO> updateSurvey(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.update(questionDTO));
    }
}