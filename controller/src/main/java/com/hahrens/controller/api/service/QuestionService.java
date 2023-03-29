package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;

import java.util.Collection;

public interface QuestionService extends DTOService<QuestionDTO>{

    Collection<QuestionDTO> findAllBySurvey(SurveyDTO surveyDTO);


}
