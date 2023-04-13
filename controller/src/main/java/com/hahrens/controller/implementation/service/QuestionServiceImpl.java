package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.DTOMapping;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final DTOMapping dtoMapping;

    public QuestionServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
    }


    @Override
    public Collection<QuestionDTO> findAll() {
        return dtoMapping.getQuestions();
    }

    @Override
    public QuestionDTO findById(final UUID primaryKey) {
        return findAll().stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public QuestionDTO create(final QuestionDTO questionDTO) {
        return (QuestionDTO) dtoMapping.addEntity(questionDTO);
    }

    @Override
    public void delete(final QuestionDTO questionDTO) {
        dtoMapping.removeEntity(questionDTO);
    }

    @Override
    public void deleteById(final UUID primaryKey) {
        QuestionDTO questionDTO = findAll().stream().filter(q -> q.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
        if (questionDTO == null) {
            return;
        }
        dtoMapping.removeEntity(questionDTO);
    }

    @Override
    public QuestionDTO update(final QuestionDTO questionDTO) {
        return (QuestionDTO) dtoMapping.updateEntity(questionDTO);
    }

    @Override
    public Collection<QuestionDTO> findAllBySurveyId(final UUID surveyId) {
        return findAll().stream().filter(q -> q.getSurveyPk().equals(surveyId)).toList();
    }

}
