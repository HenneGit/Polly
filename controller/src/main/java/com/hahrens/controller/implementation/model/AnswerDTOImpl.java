package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.dto.AnswerDTO;

import java.util.UUID;

public class AnswerDTOImpl implements AnswerDTO {

    private UUID primaryKey;
    private UUID questionPk;
    private String answerText;

    public AnswerDTOImpl(UUID primaryKey, UUID questionPk, String answerText) {
        this.primaryKey = primaryKey;
        this.questionPk = questionPk;
        this.answerText = answerText;
    }
    /**
     * default constructor for deserialization.
     */
    public AnswerDTOImpl() {
    }

    @Override
    public UUID getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public UUID getQuestionPk() {
        return questionPk;
    }

    @Override
    public String getAnswerText() {
        return answerText;
    }

}
