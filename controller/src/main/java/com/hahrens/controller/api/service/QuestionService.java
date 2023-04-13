package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;
import java.util.UUID;

/**
 * a service for managing questions.
 */
public interface QuestionService extends DTOService<QuestionDTO>{
    /**
     * find all question belonging to a given survey.
     * @param surveyId the survey to find question for.
     * @return a collection with all Questions found for the survey.
     */
    Collection<QuestionDTO> findAllBySurveyId(UUID surveyId);


}
