package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.container.QuestionContainer;
import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;

public class QuestionContainerImpl implements QuestionContainer {


    private Collection<QuestionDTO> questionDTOS;

    public QuestionContainerImpl(Collection<QuestionDTO> questionDTO) {
        this.questionDTOS = questionDTO;
    }

    @Override
    public Collection<QuestionDTO> getQuestions() {
        return questionDTOS;
    }

    @Override
    public void add(QuestionDTO questionDTO) {
        questionDTOS.add(questionDTO);
    }
}
