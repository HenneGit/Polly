package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.container.AnswerContainer;
import com.hahrens.controller.api.model.dto.QuestionDTO;

public class QuestionDTOImpl implements QuestionDTO {

    private Comparable<?> primaryKey;
    private String name;
    private String description;
    private AnswerContainer answerContainer;

    public QuestionDTOImpl(Comparable<?> primaryKey, String name, String description, AnswerContainer answerContainer) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
        this.answerContainer = answerContainer;
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
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
