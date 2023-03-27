package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.container.AnswerContainer;
import com.hahrens.controller.api.model.dto.AnswerDTO;

import java.util.Collection;

/**
 * Container object holding {@link AnswerDTO}.
 */
public class AnswerContainerImpl implements AnswerContainer {

    private Collection<AnswerDTO> answerDTOS;

    public AnswerContainerImpl(Collection<AnswerDTO> answerDTOS) {
        this.answerDTOS = answerDTOS;
    }

    @Override
    public Collection<AnswerDTO> getAnswers() {
        return answerDTOS;
    }

    @Override
    public void add(AnswerDTO answerDTO) {
        answerDTOS.add(answerDTO);
    }


}
