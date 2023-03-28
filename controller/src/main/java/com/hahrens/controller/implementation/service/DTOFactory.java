package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.controller.api.model.container.QuestionContainer;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.implementation.model.*;

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
    static QuestionDTO getQuestionDTO(QuestionEntity questionEntity, Comparable<?> surveyPk) {
        UUID primaryKey = UUID.randomUUID();
        List<AnswerEntity> answers = questionEntity.getAnswers();
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        for (AnswerEntity answer : answers) {
            answerDTOS.add(getAnswerDTO(answer, primaryKey));
        }
        AnswerContainerImpl answerContainer = new AnswerContainerImpl(answerDTOS);
        return new QuestionDTOImpl(primaryKey, questionEntity.getName(), questionEntity.getDescription(), answerContainer, surveyPk);

    }

    static AnswerDTO getAnswerDTO(AnswerEntity answerEntity, Comparable<?> questionPk) {
        return new AnswerDTOImpl(UUID.randomUUID(), questionPk, answerEntity.getAnswerText());

    }

    static SurveyDTO getSurveyDTO(final SurveyEntity surveyEntity) {
        UUID primaryKey = UUID.randomUUID();
        List<QuestionEntity> questionEntities = surveyEntity.getQuestionEntities();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntities) {
            questionDTOS.add(getQuestionDTO(questionEntity, primaryKey));
        }
        QuestionContainer questionContainer = new QuestionContainerImpl(questionDTOS);
        return new SurveyDTOImpl(primaryKey, surveyEntity.getName(), surveyEntity.getDescription(), questionContainer);
    }


}
