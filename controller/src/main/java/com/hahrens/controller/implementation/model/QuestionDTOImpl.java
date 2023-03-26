package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.container.AnswerContainer;
import com.hahrens.controller.api.model.dto.QuestionDTO;

public class QuestionDTOImpl implements QuestionDTO {

    private Comparable<?> primaryKey;
    private String name;
    private String description;
    private AnswerContainer answerContainer;
    private Comparable<?> surveyPk;

    public QuestionDTOImpl(Comparable<?> primaryKey, String name, String description, AnswerContainer answerContainer, Comparable<?> surveyPk) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
        this.answerContainer = answerContainer;
        this.surveyPk = surveyPk;
    }

    @Override
    public Comparable<?> getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public AnswerContainer getAnswerContainer() {
        return answerContainer;
    }

    @Override
    public Comparable<?> getSurveyPk() {
        return surveyPk;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
