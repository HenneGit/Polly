package com.hahrens.controller.api.model.dto;

import com.hahrens.controller.api.model.container.QuestionContainer;

public interface SurveyDTO extends Entity{


    QuestionContainer getQuestionContainer();

    String getName();

    String getDescription();

}
