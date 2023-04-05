package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;

/**
 * a service for managing answers.
 */
public interface AnswerService extends DTOService<AnswerDTO>{


    /**
     * find all answers for given question.
     * @param questionId the question to get the answers for.
     * @return all answers found for given question.
     */
    Collection<AnswerDTO> findAllByQuestionId(Comparable<?> questionId);


}
