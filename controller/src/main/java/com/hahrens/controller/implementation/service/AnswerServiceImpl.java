package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.api.service.DTOMapping;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final DTOMapping dtoMapping;

    public AnswerServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
    }


    @Override
    public Collection<AnswerDTO> findAll() {
        return dtoMapping.getAnswers();
    }

    @Override
    public AnswerDTO findById(final UUID primaryKey) {
        return dtoMapping.getAnswers().stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public AnswerDTO create(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        DTOEntityInterface dtoEntityInterface = dtoMapping.addEntity(answerDTO);
        return (AnswerDTO) dtoEntityInterface;
    }

    @Override
    public void delete(final AnswerDTO answerDTO) {
        dtoMapping.removeEntity(answerDTO);
    }

    @Override
    public void deleteById(final UUID primaryKey) {
        AnswerDTO answerDTO = dtoMapping.getAnswers().stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
        if (answerDTO == null) {
            return;
        }
        //todo remove by id.
        dtoMapping.removeEntity(answerDTO);
    }

    @Override
    public AnswerDTO update(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        return (AnswerDTO) dtoMapping.updateEntity(answerDTO);
    }

    @Override
    public Collection<AnswerDTO> findAllByQuestionId(final UUID questionId) {
        if (questionId == null) {
            return null;
        }
        return findAll().stream().filter(a -> a.getQuestionPk().equals(questionId)).toList();
    }
}
