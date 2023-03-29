package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;

import java.util.Collection;

public interface DTOMapping {

    Collection<AnswerDTO> getAnswers();

    Collection<QuestionDTO> getQuestions();

    Collection<SurveyDTO> getSurveys();

    void persistDTOs(Collection<? extends DTOEntityInterface> dtos, Class<? extends DTOEntityInterface> clazz);
}
