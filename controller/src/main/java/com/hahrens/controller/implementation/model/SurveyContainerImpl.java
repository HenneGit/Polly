package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.container.SurveyContainer;
import com.hahrens.controller.api.model.dto.SurveyDTO;

import java.util.Collection;

public class SurveyContainerImpl implements SurveyContainer {


    private Collection<SurveyDTO> surveyDTOS;

    public SurveyContainerImpl(Collection<SurveyDTO> surveyDTOS) {
        this.surveyDTOS = surveyDTOS;
    }

    @Override
    public Collection<SurveyDTO> getSurveys() {
        return null;
    }

    @Override
    public void add(SurveyDTO surveyDTO) {
        surveyDTOS.add(surveyDTO);
    }
}
