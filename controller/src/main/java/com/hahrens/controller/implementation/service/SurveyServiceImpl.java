package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.DTOMapping;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class SurveyServiceImpl implements SurveyService {

    private DTOMapping dtoMapping;
    private List<SurveyDTO> surveyDTOS;

    public SurveyServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
        surveyDTOS = new ArrayList<>();
    }


    @PreDestroy
    public void save() {
        dtoMapping.save(surveyDTOS, SurveyDTO.class);
    }


    @Override
    public Collection<SurveyDTO> findAll() {
        if (surveyDTOS.isEmpty()) {
            surveyDTOS.addAll(dtoMapping.getSurveys());
        }
        return surveyDTOS;
    }

    @Override
    public SurveyDTO findById(final UUID primaryKey) {
        return surveyDTOS.stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public SurveyDTO create(final SurveyDTO surveyDTO) {
        SurveyDTO answerDTO1 = new SurveyDTOImpl(UUID.randomUUID(), surveyDTO.getName(), surveyDTO.getDescription());
        surveyDTOS.add(answerDTO1);
        return answerDTO1;
    }

    @Override
    public void delete(final SurveyDTO surveyDTO) {
        SurveyDTO byId = findById(surveyDTO.getPrimaryKey());
        surveyDTOS.remove(byId);
    }

    @Override
    public void deleteById(UUID primaryKey) {
        SurveyDTO surveyDTO = surveyDTOS.stream().filter(s -> s.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
        if (surveyDTO == null) {
            return;
        }
        surveyDTOS.remove(surveyDTO);
    }

    @Override
    public SurveyDTO update(final SurveyDTO surveyDTO) {
        SurveyDTO oldAnswerDTO = findById(surveyDTO.getPrimaryKey());
        if (oldAnswerDTO == null) {
            return null;
        }
        surveyDTOS.remove(oldAnswerDTO);
        SurveyDTO updatedAnswerDTO = new SurveyDTOImpl(surveyDTO.getPrimaryKey(), surveyDTO.getName(), surveyDTO.getDescription());
        surveyDTOS.add(updatedAnswerDTO);
        return updatedAnswerDTO;
    }

}
