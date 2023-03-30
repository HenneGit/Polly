package com.hahrens.controller.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.controller.api.service.DTOService;
import com.hahrens.controller.implementation.service.DTOMappingImpl;
import com.hahrens.controller.service.mocks.AnswerEntityRepositoryMock;
import com.hahrens.controller.service.mocks.QuestionEntityRepositoryMock;
import com.hahrens.controller.service.mocks.SurveyEntityRepositoryMock;

import java.util.List;

/**
 * simple class for setting up repository mocks for all repository tests.
 */
public class TestSetup {


    private final DTOMappingImpl dtoMapping;

    public TestSetup() {
        SurveyEntityRepositoryMock surveyEntityRepositoryMock = new SurveyEntityRepositoryMock();
        List<SurveyEntity> surveys = surveyEntityRepositoryMock.findAll();
        QuestionEntityRepositoryMock questionEntityRepositoryMock = new QuestionEntityRepositoryMock(surveys.get(0), surveys.get(1));
        List<QuestionEntity> questionEntities = questionEntityRepositoryMock.findAll();
        AnswerEntityRepositoryMock answerEntityRepositoryMock = new AnswerEntityRepositoryMock(questionEntities.get(0), questionEntities.get(1));
        dtoMapping = new DTOMappingImpl(questionEntityRepositoryMock, answerEntityRepositoryMock, surveyEntityRepositoryMock);
        List<AnswerEntity> allAnswers = answerEntityRepositoryMock.findAll();
        questionEntityRepositoryMock.setAnswers(allAnswers);
        List<QuestionEntity> allQuestions = questionEntityRepositoryMock.findAll();
        surveyEntityRepositoryMock.setQuestions(allQuestions);

    }

    public DTOMappingImpl getDtoMapping() {
        return dtoMapping;
    }

    /**
     * store changes and pull stored data.
     * @param dtoService the service to persist.
     */
    public void resetDtoMapping(final DTOService<?> dtoService) {
        dtoService.persistChanges();
        dtoMapping.load();

    }
}
