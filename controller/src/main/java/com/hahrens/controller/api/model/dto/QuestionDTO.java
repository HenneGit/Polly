package com.hahrens.controller.api.model.dto;

import com.hahrens.controller.api.model.container.AnswerContainer;

public interface QuestionDTO extends DTOEntityInterface {

    AnswerContainer getAnswerContainer();

    Comparable<?> getSurveyPk();

    String getName();

    String getDescription();


}
