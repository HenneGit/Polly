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

    /**
     * remove an entity from storage.
     * @param dtoEntityInterface the entity to remove.
     */
    void removeEntity(DTOEntityInterface dtoEntityInterface);

    /**
     * add an entity to storage.
     * @param dtoEntityInterface the entity to add.
     * @return the new dto object.
     */
    DTOEntityInterface addEntity(DTOEntityInterface dtoEntityInterface);

    /**
     * update the given dto entity.
     * @param dtoEntityInterface the entity to update.
     * @return the updated dto entity.
     */
    DTOEntityInterface updateEntity(DTOEntityInterface dtoEntityInterface);
}
