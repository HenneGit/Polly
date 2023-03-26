package com.hahrens.controller.service.mocks;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.QuestionService;

import java.util.Collection;
import java.util.List;

public class QuestionServiceMock implements QuestionService {

    private List<QuestionDTO> repo;




    @Override
    public Collection<QuestionDTO> findAll() {
        return null;
    }

    @Override
    public QuestionDTO findById(Comparable<?> primaryKey) {
        return null;
    }

    @Override
    public void remove(QuestionDTO questionDTO) {

    }

    @Override
    public QuestionDTO create(QuestionDTO questionDTO) {
        return null;
    }

    @Override
    public QuestionDTO update(QuestionDTO questionDTO) {
        return null;
    }

    @Override
    public Collection<QuestionDTO> findAllBySurvey(SurveyDTO surveyDTO) {
        return null;
    }
}
