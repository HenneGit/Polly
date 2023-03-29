package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.DTOMapping;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class SurveyServiceImpl implements SurveyService {

    private DTOMapping dtoMapping;
    private Collection<SurveyDTO> surveyDTOS;

    public SurveyServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
        surveyDTOS = dtoMapping.getSurveys();
    }

    @PreDestroy
    public void persistChanges() {
        dtoMapping.persistDTOs(surveyDTOS, QuestionDTO.class);
    }


    @Override
    public Collection<SurveyDTO> findAll() {
        return surveyDTOS;
    }

    @Override
    public SurveyDTO findById(final Comparable<?> primaryKey) {
        return surveyDTOS.stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public SurveyDTO create(final SurveyDTO surveyDTO) {
        SurveyDTO answerDTO1 = new SurveyDTOImpl(UUID.randomUUID(), surveyDTO.getName(), surveyDTO.getDescription(), surveyDTO.getQuestionContainer());
        surveyDTOS.add(answerDTO1);
        return answerDTO1;
    }

    @Override
    public void remove(final SurveyDTO surveyDTO) {
        surveyDTOS.remove(surveyDTO);
    }

    @Override
    public SurveyDTO update(final SurveyDTO surveyDTO) {
        SurveyDTO oldAnswerDTO = surveyDTOS.stream().filter(a -> a.getPrimaryKey().equals(surveyDTO.getPrimaryKey())).findFirst().orElse(null);
        surveyDTOS.remove(oldAnswerDTO);
        SurveyDTO updatedAnswerDTO = new SurveyDTOImpl(UUID.randomUUID(), surveyDTO.getName(), surveyDTO.getDescription(), surveyDTO.getQuestionContainer());
        surveyDTOS.add(updatedAnswerDTO);
        return updatedAnswerDTO;
    }

}
