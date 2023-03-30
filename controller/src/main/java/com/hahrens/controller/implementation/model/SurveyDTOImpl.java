package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.container.QuestionContainer;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.model.dto.SurveyDTO;

public class SurveyDTOImpl implements SurveyDTO {

    private QuestionContainer questionContainer;
    private Comparable<?> primaryKey;
    private String name;
    private String description;

    public SurveyDTOImpl(Comparable<?> primaryKey, String name, String description, QuestionContainer questionContainer) {
        this.questionContainer = questionContainer;
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
    }

    @Override
    public Comparable<?> getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public QuestionContainer getQuestionContainer() {
        return questionContainer;
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
