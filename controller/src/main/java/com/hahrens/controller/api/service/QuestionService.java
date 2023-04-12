package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;
import java.util.UUID;

/**
 * a service for managing questions.
 */
public interface QuestionService extends DTOService<QuestionDTO>{

    Collection<QuestionDTO> findAllBySurveyId(UUID surveyId);


}
