package com.hahrens.webapp.rest;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * a rest controller for delivering answerDtos to the frontend.
 */
@RestController
@RequestMapping("/answer")
public class AnswerRestController {


    private final AnswerService answerService;

    public AnswerRestController(AnswerService answerService) {
        this.answerService = answerService;
    }

    /**
     * get all answers.
     * @return all found answers.
     */
    @GetMapping("/get")
    public ResponseEntity<Collection<AnswerDTO>> getAnswers() {
        Collection<AnswerDTO> all = answerService.findAll();
        if (all == null || all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    /**
     * find answer by id.
     * @return the answer found for that id.
     */
    @RequestMapping(value = "/{answerId}", method = RequestMethod.GET)
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable String answerId)    {
        if (answerId == null) {
            return ResponseEntity.badRequest().build();
        }
        AnswerDTO byId = answerService.findById(answerId);

        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId);
    }

    /**
     * get all answers for a given question.
     * @return all found answers.
     */
    @GetMapping("/getByQuestion")
    public ResponseEntity<Collection<AnswerDTO>> getAnswersByQuestion(@RequestBody QuestionDTOImpl question)    {
        if (question == null) {
            return ResponseEntity.badRequest().build();
        }
        Collection<AnswerDTO> allByQuestion = answerService.findAllByQuestion(question);
        if (allByQuestion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allByQuestion);
    }


    /**
     * add the given answer.
     * @param answerDTO the answer to add.
     * @return the created answer with pk.
     */
    @PostMapping("/add")
    public ResponseEntity<AnswerDTO> createAnswer(@RequestBody AnswerDTOImpl answerDTO) {
        AnswerDTO createdAnswerDTO = answerService.create(answerDTO);
        if (createdAnswerDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(null).body(createdAnswerDTO);
    }

    /**
     * delete the given answer.
     * @param answerId the answer to delete.
     * @return confirmation that answer has been deleted.
     */
    @RequestMapping(value = "/delete/{answerId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAnswer(@PathVariable String answerId) {
        answerService.deleteById(answerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * update given answer.
     * @param answerDTO the answer to update.
     * @return the updated answer.
     */
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerDTO> updateAnswer(@RequestBody AnswerDTOImpl answerDTO) {
        AnswerDTO update = answerService.update(answerDTO);
        if (update == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(update);
    }
}
