package com.hahrens.controller.api.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;

import java.util.Collection;

public interface AnswerService extends DTOService<AnswerDTO>{

    Collection<AnswerDTO> findAllByQuestion(QuestionDTO questionDTO);


}
