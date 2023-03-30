package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.dto.AnswerDTO;

public class AnswerDTOImpl implements AnswerDTO {

    private Comparable<?> primaryKey;
    private Comparable<?> questionPk;
    private String answerText;

    public AnswerDTOImpl(Comparable<?> primaryKey, Comparable<?> questionPk, String answerText) {
        this.primaryKey = primaryKey;
        this.questionPk = questionPk;
        this.answerText = answerText;
    }

    @Override
    public Comparable<?> getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public Comparable<?> getQuestionPk() {
        return questionPk;
    }

    @Override
    public String getAnswerText() {
        return answerText;
    }

}
