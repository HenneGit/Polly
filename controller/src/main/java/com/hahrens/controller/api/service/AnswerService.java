package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import jakarta.annotation.PreDestroy;

import java.util.Collection;

public interface AnswerService extends DTOService<AnswerDTO>{


    /**
     * find all answers for given question.
     * @param questionDTO the question to get the answers for.
     * @return all answers found for given question.
     */
    Collection<AnswerDTO> findAllByQuestion(QuestionDTO questionDTO);


}
