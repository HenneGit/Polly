package com.hahrens.controller.api.model.container;

import com.hahrens.controller.api.model.dto.AnswerDTO;

import java.util.Collection;

public interface AnswerContainer {

    Collection<AnswerDTO> getAnswers();

    void add(AnswerDTO answerDTO);

}
