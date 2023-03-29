package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;

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
    public boolean equals(DTOEntityInterface dtoEntityInterface) {
        if (dtoEntityInterface instanceof AnswerDTO answerDTO) {
            if (!answerDTO.getAnswerText().equals(this.answerText)) {
                return false;
            }
            if (!answerDTO.getPrimaryKey().equals(this.primaryKey)) {
                return false;
            }
            if (!answerDTO.getQuestionPk().equals(this.questionPk)) {
                return false;
            }
        }
        return true;
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
