package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.controller.api.model.container.QuestionContainer;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.model.QuestionContainerImpl;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;

import java.util.ArrayList;
import java.util.List;
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
     * @param questionEntity
     * @param surveyPk
     * @return
     */
    static QuestionDTO getQuestionDTO(QuestionEntity questionEntity, UUID surveyPk) {
        UUID primaryKey = UUID.randomUUID();
        List<AnswerEntity> answers = questionEntity.getAnswers();
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        if (answers != null) {
            for (AnswerEntity answer : answers) {
                answerDTOS.add(getAnswerDTO(answer, primaryKey));
            }
        }
        return new QuestionDTOImpl(primaryKey, questionEntity.getName(), questionEntity.getDescription(), questionEntity.getQuestion(), surveyPk);

    }

    static AnswerDTO getAnswerDTO(AnswerEntity answerEntity, UUID questionPk) {
        return new AnswerDTOImpl(UUID.randomUUID(), questionPk, answerEntity.getAnswerText());

    }

    static SurveyDTO getSurveyDTO(final SurveyEntity surveyEntity) {
        UUID primaryKey = UUID.randomUUID();
        List<QuestionEntity> questionEntities = surveyEntity.getQuestionEntities();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        if (questionEntities != null) {

            for (QuestionEntity questionEntity : questionEntities) {
                questionDTOS.add(getQuestionDTO(questionEntity, primaryKey));
            }
        }
        QuestionContainer questionContainer = new QuestionContainerImpl(questionDTOS);
        return new SurveyDTOImpl(primaryKey, surveyEntity.getName(), surveyEntity.getDescription());
    }


}
