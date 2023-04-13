package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;

import java.util.Collection;

/**
 * Maps storage objects to {@link DTOEntityInterface} objects and caches them for use in services. When work is done
 * all changes are persisted in db.
 */
public interface DTOMapping {

    void load();

    /**
     * get all answers.
     * @return collection of all answers.
     */
    Collection<AnswerDTO> getAnswers();

    /**
     * get all QuestionDTOs.
     * @return collection of all questions.
     */
    Collection<QuestionDTO> getQuestions();

    /**
     * get all SurveyDTOs.
     * @return collection of all surveys.
     */
    Collection<SurveyDTO> getSurveys();


    void removeEntity(DTOEntityInterface dtoEntityInterface);

    DTOEntityInterface addEntity(DTOEntityInterface dtoEntityInterface);

    DTOEntityInterface updateEntity(DTOEntityInterface dtoEntityInterface);
}
