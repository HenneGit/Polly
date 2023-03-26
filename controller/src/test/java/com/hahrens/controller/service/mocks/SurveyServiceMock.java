package com.hahrens.controller.service.mocks;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;

import java.util.Collection;

public class SurveyServiceMock implements SurveyService {
    @Override
    public Collection<SurveyDTO> findAll() {
        return null;
    }

    @Override
    public SurveyDTO findById(Comparable<?> primaryKey) {
        return null;
    }

    @Override
    public void remove(SurveyDTO surveyDTO) {

    }

    @Override
    public SurveyDTO create(SurveyDTO surveyDTO) {
        return null;
    }

    @Override
    public SurveyDTO update(SurveyDTO surveyDTO) {
        return null;
    }
}
