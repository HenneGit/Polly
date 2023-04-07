package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;

import java.util.UUID;

/**
 * Factory creating dtos from storage objects.
 */
final class DTOFactory {

    private DTOFactory() {
        //hide constructor
    }

    /**
     * produce question dto from questionEntity.
     * @param questionEntity the questionEntity to create dto from.
     * @param surveyPk the survey pk this question belongs to.
     * @return the dto object.
     */
    static QuestionDTO getQuestionDTO(final QuestionEntity questionEntity, final UUID surveyPk) {
        UUID primaryKey = UUID.randomUUID();
        return new QuestionDTOImpl(primaryKey, questionEntity.getName(), questionEntity.getDescription(), questionEntity.getQuestion(), surveyPk);
    }

    /**
     * produce a answerDTO from answerEntity.
     * @param answerEntity the entity to create dto from.
     * @param questionPk the question this answer belongs to.
     * @return the dto object.
     */
    static AnswerDTO getAnswerDTO(final AnswerEntity answerEntity, final UUID questionPk) {
        return new AnswerDTOImpl(UUID.randomUUID(), questionPk, answerEntity.getAnswerText());

    }

    /**
     * produce a survey dto from surveyEntity.
     * @param surveyEntity the surveyEntity to  create dto from.
     * @return the dto object.
     */
    static SurveyDTO getSurveyDTO(final SurveyEntity surveyEntity) {
        UUID primaryKey = UUID.randomUUID();
        return new SurveyDTOImpl(primaryKey, surveyEntity.getName(), surveyEntity.getDescription());
    }


}
