package com.hahrens.controller.implementation.service.dto;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.dto.DTOMapping;
import com.hahrens.controller.api.service.dto.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
