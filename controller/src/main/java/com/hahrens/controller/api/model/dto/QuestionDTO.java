package com.hahrens.controller.api.model.dto;

import com.hahrens.controller.api.model.container.AnswerContainer;
import com.hahrens.controller.api.model.container.Container;

public interface QuestionDTO extends Entity{

    AnswerContainer getAnswerContainer();

    String getName();

    String getDescription();


}
