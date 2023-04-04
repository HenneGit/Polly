package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.UUID;

public class QuestionDTOImpl implements QuestionDTO {

    private UUID primaryKey;
    private String name;
    private String description;
    private String question;
    private UUID surveyPk;

    public QuestionDTOImpl(UUID primaryKey, String name, String description, String question, UUID surveyPk) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
        this.question = question;
        this.surveyPk = surveyPk;
    }

    /**
     * default constructor for deserialization.
     */
    public QuestionDTOImpl() {
    }

    @Override
    public UUID getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public UUID getSurveyPk() {
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

    @Override
    public String getQuestion() {
        return question;
    }
}
