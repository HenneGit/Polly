package com.hahrens.controller.api.model.dto;

import java.util.UUID;

/**
 * a data transfer object representing a question.
 */
public interface QuestionDTO extends DTOEntityInterface {
    /**
     * get the pk of the survey this question is in.
     * @return the survey pk.
     */
    UUID getSurveyPk();

    /**
     * get the name of this question.
     * @return the name.
     */
    String getName();

    /**
     * get the description of this question.
     * @return the description
     */
    String getDescription();

    /**
     * get the question text of this question.
     * @return the question text.
     */
    String getQuestion();


}
