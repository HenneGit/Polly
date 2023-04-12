package com.hahrens.controller.implementation.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;
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
        questionDTOS = new ArrayList<>();
    }

    @PreDestroy
    public void save() {
        dtoMapping.save(questionDTOS, QuestionDTO.class);
    }


    @Override
    public Collection<QuestionDTO> findAll() {
        load();
        return questionDTOS;
    }

    private void load() {
        if (questionDTOS.isEmpty()) {
            questionDTOS.addAll(dtoMapping.getQuestions());
        }
    }

    @Override
    public QuestionDTO findById(final UUID primaryKey) {
        load();

        return questionDTOS.stream().filter(a -> a.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
    }

    @Override
    public QuestionDTO create(final QuestionDTO questionDTO) {
        load();
        QuestionDTO answerDTO1 = new QuestionDTOImpl(UUID.randomUUID(), questionDTO.getName(), questionDTO.getDescription(), questionDTO.getQuestion(), questionDTO.getSurveyPk());
        questionDTOS.add(answerDTO1);
        return answerDTO1;
    }

    @Override
    public void delete(final QuestionDTO questionDTO) {
        load();
        questionDTOS.remove(questionDTO);
    }

    @Override
    public void deleteById(final UUID primaryKey) {
        QuestionDTO questionDTO = questionDTOS.stream().filter(q -> q.getPrimaryKey().equals(primaryKey)).findFirst().orElse(null);
        if (questionDTO == null) {
            return;
        }
        questionDTOS.remove(questionDTO);
    }

    @Override
    public QuestionDTO update(final QuestionDTO questionDTO) {
        load();
        QuestionDTO oldAnswerDTO = questionDTOS.stream().filter(a -> a.getPrimaryKey().equals(questionDTO.getPrimaryKey())).findFirst().orElse(null);
        questionDTOS.remove(oldAnswerDTO);
        QuestionDTO updatedAnswerDTO = new QuestionDTOImpl(questionDTO.getPrimaryKey(), questionDTO.getName(), questionDTO.getDescription(), questionDTO.getQuestion(), questionDTO.getSurveyPk());
        questionDTOS.add(updatedAnswerDTO);
        save();
        load();
        return updatedAnswerDTO;
    }

    @Override
    public Collection<QuestionDTO> findAllBySurveyId(final Comparable<?> surveyId) {
        load();
        return questionDTOS.stream().filter(q -> q.getSurveyPk().toString().equals(surveyId)).toList();
    }

}
