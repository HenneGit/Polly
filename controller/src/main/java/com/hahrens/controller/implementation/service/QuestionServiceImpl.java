package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.DTOMapping;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private DTOMapping dtoMapping;
    private List<QuestionDTO> questionDTOS;

    public QuestionServiceImpl(DTOMapping dtoMapping) {
        this.dtoMapping = dtoMapping;
        questionDTOS = new ArrayList<>(dtoMapping.getQuestions());
    }

    @PreDestroy
    public void save() {
        dtoMapping.save(questionDTOS, QuestionDTO.class);
    }


    @Override
    public Collection<QuestionDTO> findAll() {
        return questionDTOS;
    }

    @Override
    public QuestionDTO findById(final Comparable<?> primaryKey) {
        return questionDTOS.stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public QuestionDTO create(final QuestionDTO questionDTO) {
        QuestionDTO answerDTO1 = new QuestionDTOImpl(UUID.randomUUID(), questionDTO.getName(), questionDTO.getDescription(), questionDTO.getQuestion(), null, questionDTO.getSurveyPk());
        questionDTOS.add(answerDTO1);
        return answerDTO1;
    }

    @Override
    public void remove(final QuestionDTO questionDTO) {
        questionDTOS.remove(questionDTO);
    }

    @Override
    public QuestionDTO update(final QuestionDTO questionDTO) {
        QuestionDTO oldAnswerDTO = questionDTOS.stream().filter(a -> a.getPrimaryKey().equals(questionDTO.getPrimaryKey())).findFirst().orElse(null);
        questionDTOS.remove(oldAnswerDTO);
        QuestionDTO updatedAnswerDTO = new QuestionDTOImpl(questionDTO.getPrimaryKey(), questionDTO.getName(), questionDTO.getDescription(), questionDTO.getQuestion(), null, questionDTO.getSurveyPk());
        questionDTOS.add(updatedAnswerDTO);
        return updatedAnswerDTO;
    }

    @Override
    public Collection<QuestionDTO> findAllBySurvey(final SurveyDTO surveyDTO) {
        return questionDTOS.stream().filter(q -> q.getSurveyPk().equals(surveyDTO.getPrimaryKey())).toList();
    }

}
