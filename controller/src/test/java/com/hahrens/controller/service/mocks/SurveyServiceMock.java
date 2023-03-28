package com.hahrens.controller.service.mocks;

import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyServiceMock implements SurveyService {


    private Map<Comparable<?>, SurveyDTO> repo;


    public SurveyServiceMock(List<SurveyDTO> list) {
        repo = new HashMap<>();
        list.forEach(q -> repo.put(q.getPrimaryKey(), q));
    }



    @Override
    public Collection<SurveyDTO> findAll() {
        return null;
    }

    @Override
    public SurveyDTO findById(Comparable<?> primaryKey) {
        return repo.get(primaryKey);
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
