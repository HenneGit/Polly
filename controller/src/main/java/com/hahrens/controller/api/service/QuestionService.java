package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;

/**
 * a service for managing questions.
 */
public interface QuestionService extends DTOService<QuestionDTO>{

    Collection<QuestionDTO> findAllBySurveyId(Comparable<?> surveyId);


}
