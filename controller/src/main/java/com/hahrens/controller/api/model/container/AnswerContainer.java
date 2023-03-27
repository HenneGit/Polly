package com.hahrens.controller.api.model.container;

import com.hahrens.controller.api.model.dto.AnswerDTO;

import java.util.Collection;

/**
 * interface for AnswerContainer.
 */
public interface AnswerContainer {
    /**
     * get all AnswerDTOs in container.
     * @return all answers.
     */
    Collection<AnswerDTO> getAnswers();

    /**
     * add an answer.
     * @param answerDTO the answer to add.
     */
    void add(AnswerDTO answerDTO);

}
