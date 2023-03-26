package com.hahrens.controller.api.model.dto;

public interface AnswerDTO extends DTOEntityInterface {

    Comparable<?> getQuestionPk();

    String getAnswerText();


}
