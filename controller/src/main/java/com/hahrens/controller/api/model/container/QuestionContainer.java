package com.hahrens.controller.api.model.container;

import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;

public interface QuestionContainer {

    Collection<QuestionDTO> getQuestions();
}
