package com.hahrens.controller.api.model.dto;

import java.util.UUID;

public interface AnswerDTO extends DTOEntityInterface {

    UUID getQuestionPk();

    String getAnswerText();


}
