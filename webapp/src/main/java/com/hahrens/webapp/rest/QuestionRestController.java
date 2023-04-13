package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

/**
 * rest controller for delivering questions to frontend.
 */
@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:5173")
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
        Collection<QuestionDTO> all = questionService.findAll();
        if (all == null || all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    /**
     * find question by id.
     * @return the question found for that id.
     */
    @RequestMapping(value = "getById/{questionId}", method = RequestMethod.GET)
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable UUID questionId) {

        if (questionId == null) {
            return ResponseEntity.badRequest().build();
        }
        QuestionDTO byId = questionService.findById(questionId);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId);
    }


    /**
     * get all answers for a given question.
     * @return all found answers.
     */
    @RequestMapping(value = "getBySurveyId/{surveyId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<QuestionDTO>> getQuestionBySurveyId(@PathVariable UUID surveyId)    {
        //todo add pk not found exception.
        if (surveyId == null) {
            return ResponseEntity.badRequest().build();
        }
        Collection<QuestionDTO> allByQuestion = questionService.findAllBySurveyId(surveyId);
        if (allByQuestion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allByQuestion);
    }



    /**
     * add a new question.
     * @param questionDTO the question to add.
     * @return the created question.
     */
    @PostMapping("/add")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTOImpl questionDTO) {
        QuestionDTO createdQuestion = questionService.create(questionDTO);
        if (createdQuestion == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(null).body(createdQuestion);
    }


    /**
     * delete the given question.
     * @param questionId the question to delete.
     * @return confirmation that question has been deleted.
     */
    @RequestMapping(value = "/delete/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteQuestion(@PathVariable UUID questionId) {
        //todo add pk not found exception.
        questionService.deleteById(questionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * update the given question.
     * @param questionDTO the question to update.
     * @return the updated question.
     */
    @PutMapping("update")
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody QuestionDTOImpl questionDTO) {
        QuestionDTO update = questionService.update(questionDTO);
        if (update == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(update);
    }
}