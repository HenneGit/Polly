package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * rest controller for delivering questions to frontend.
 */
@RestController
@RequestMapping("/question")
public class QuestionRestController {


    private final QuestionService questionService;

    public QuestionRestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * get all questions.
     * @return all questions found.
     */
    @GetMapping("/get")
    public ResponseEntity<Collection<QuestionDTO>> getQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    /**
     * add a new question.
     * @param questionDTO the question to add.
     * @return the created question.
     */
    @PostMapping("/add")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTOImpl questionDTO) {
        return ResponseEntity.ok(questionService.create(questionDTO));
    }

    /**
     * delete the given question.
     * @param questionDTO the question to delete.
     * @return a confirmation that the question has been deleted.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuestion(@RequestBody QuestionDTOImpl questionDTO) {
        questionService.remove(questionDTO);
        return ResponseEntity.ok("deleted");
    }

    /**
     * update the given question.
     * @param questionDTO the question to update.
     * @return the updated question.
     */
    @PutMapping("update")
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody QuestionDTOImpl questionDTO) {
        return ResponseEntity.ok(questionService.update(questionDTO));
    }
}