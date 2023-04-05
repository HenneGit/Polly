package com.hahrens.controller.api.model.dto;

import java.util.UUID;

/**
 * a data transfer object for a answer option.
 */
public interface AnswerDTO extends DTOEntityInterface {

    UUID getQuestionPk();

    String getAnswerText();


}
