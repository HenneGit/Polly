package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.api.service.DTOMapping;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {

    private DTOMapping dtoMapping;
    private List<AnswerDTO> answerDTOS;

    public AnswerServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
        answerDTOS = new ArrayList<>();
        answerDTOS.addAll(dtoMapping.getAnswers());
    }

    @PreDestroy
    @Override
    public void persistChanges() {
        dtoMapping.persistDTOs(answerDTOS, AnswerDTO.class);
    }


    @Override
    public Collection<AnswerDTO> findAll() {
          return answerDTOS;
    }

    @Override
    public AnswerDTO findById(final Comparable<?> primaryKey) {
        return answerDTOS.stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public AnswerDTO create(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        AnswerDTOImpl answerDTO1 = new AnswerDTOImpl(UUID.randomUUID(), answerDTO.getQuestionPk(), answerDTO.getAnswerText());
        answerDTOS.add(answerDTO1);
        return answerDTO1;
    }

    @Override
    public void remove(final AnswerDTO answerDTO) {
        answerDTOS.remove(answerDTO);
    }

    @Override
    public AnswerDTO update(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        AnswerDTO oldAnswerDTO = answerDTOS.stream().filter(a -> a.getPrimaryKey().equals(answerDTO.getPrimaryKey())).findFirst().orElse(null);
        if (oldAnswerDTO == null) {
            return null;
        }
        answerDTOS.remove(oldAnswerDTO);
        AnswerDTOImpl updatedAnswerDTO = new AnswerDTOImpl(answerDTO.getPrimaryKey(), answerDTO.getAnswerText(), answerDTO.getAnswerText());
        answerDTOS.add(updatedAnswerDTO);
        return answerDTO;
    }

    @Override
    public Collection<AnswerDTO> findAllByQuestion(final QuestionDTO questionDTO) {
       return answerDTOS.stream().filter(a -> a.getQuestionPk().equals(questionDTO.getPrimaryKey())).toList();
    }
}