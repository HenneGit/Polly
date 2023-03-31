package com.hahrens.controller.api.model.dto;

import java.util.UUID;

public interface QuestionDTO extends DTOEntityInterface {

    UUID getSurveyPk();

    String getName();

    String getDescription();

    String getQuestion();


}
