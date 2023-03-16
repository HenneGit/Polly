package com.hahrens.controller.api.model.container;

import com.hahrens.controller.api.model.dto.SurveyDTO;

import java.util.Collection;

public interface SurveyContainer {

    Collection<SurveyDTO> getSurveys();
}
