package com.hahrens.controller.service.mocks;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.QuestionService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionServiceMock implements QuestionService {

    private Map<Comparable<?>, QuestionDTO> repo;


    public QuestionServiceMock(List<QuestionDTO> list) {
        repo = new HashMap<>();
        list.forEach(q -> repo.put(q.getPrimaryKey(), q));
    }

    @Override
    public Collection<QuestionDTO> findAll() {
        return repo.values();
    }

    @Override
    public QuestionDTO findById(Comparable<?> primaryKey) {
        return repo.get(primaryKey);
    }

    @Override
    public void remove(QuestionDTO questionDTO) {
        repo.remove(questionDTO.getPrimaryKey());
    }

    @Override
    public QuestionDTO create(QuestionDTO questionDTO) {
        repo.put(questionDTO.getPrimaryKey(), questionDTO);
        return questionDTO;

    }

    @Override
    public QuestionDTO update(QuestionDTO questionDTO) {
        return null;

    }

    @Override
    public Collection<QuestionDTO> findAllBySurvey(SurveyDTO surveyDTO) {
        return repo.values().stream().filter(q -> q.getSurveyPk().equals(surveyDTO.getPrimaryKey())).toList();

    }
}
