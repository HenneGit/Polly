package com.hahrens.controller.implementation.service.dto;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.dto.DTOMapping;
import com.hahrens.controller.api.service.dto.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final DTOMapping dtoMapping;

    public SurveyServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
    }

    @Override
    public Collection<SurveyDTO> findAll() {
        return dtoMapping.getSurveys();
    }

    @Override
    public SurveyDTO findById(final UUID primaryKey) {
        return findAll().stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public SurveyDTO create(final SurveyDTO surveyDTO) {
        SurveyDTO answerDTO1 = new SurveyDTOImpl(UUID.randomUUID(), surveyDTO.getName(), surveyDTO.getDescription());
        dtoMapping.addEntity(answerDTO1);
        return answerDTO1;
    }

    @Override
    public void delete(final SurveyDTO surveyDTO) {
        SurveyDTO byId = findById(surveyDTO.getPrimaryKey());
        dtoMapping.removeEntity(byId);
    }

    @Override
    public void deleteById(UUID primaryKey) {
        SurveyDTO surveyDTO = findAll().stream().filter(s -> s.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
        if (surveyDTO == null) {
            return;
        }
        dtoMapping.removeEntity(surveyDTO);
    }

    @Override
    public SurveyDTO update(final SurveyDTO surveyDTO) {
        return (SurveyDTO) dtoMapping.updateEntity(surveyDTO);
    }

}
