package com.hahrens.controller.api.model.dto;

/**
 * a data transfer object representing a survey.
 */
public interface SurveyDTO extends DTOEntityInterface {
    /**
     * get the name of this survey.
     * @return the name.
     */
    String getName();

    /**
     * get the description of this survey.
     * @return the description.
     */
    String getDescription();

}
