package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/answer")
public class AnswerRestController {


    private final AnswerService answerService;

    public AnswerRestController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/answers")
    public ResponseEntity<Collection<AnswerDTO>> getSurveys() {
        return ResponseEntity.ok(answerService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<AnswerDTO> createSurvey(@RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.create(answerDTO));
    }

    @DeleteMapping("/deleteAnswer")
    public ResponseEntity<String> deleteSurvey(@RequestBody AnswerDTO answerDTO) {
        answerService.remove(answerDTO);
        return ResponseEntity.ok("deleted");
    }

    @PutMapping("/updateAnswer")
    public ResponseEntity<AnswerDTO> updateSurvey(@RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.update(answerDTO));
    }
}
