package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.repository.QuestionEntityRepository;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.api.service.SurveyService;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Map<QuestionDTO, QuestionEntity> dtoMapping;

    private final QuestionEntityRepository questionEntityRepository;

    private final SurveyService surveyService;

    public QuestionServiceImpl(QuestionEntityRepository questionEntityRepository, SurveyService surveyService) {
        this.questionEntityRepository = questionEntityRepository;
        this.surveyService = surveyService;
        dtoMapping = new HashMap<>();

    }

    @PreDestroy
    private void flush() {
        questionEntityRepository.flush();
    }


    @Override
    public Collection<QuestionDTO> findAll() {
        load();
        return dtoMapping.keySet();
    }


    @Override
    public QuestionDTO findById(final Comparable<?> primaryKey) {
        load();
        if (primaryKey == null) {
            return null;
        }
        Set<Map.Entry<QuestionDTO, QuestionEntity>> entries = dtoMapping.entrySet();
        for (Map.Entry<QuestionDTO, QuestionEntity> entry : entries) {
            if (entry.getKey().getPrimaryKey().equals(primaryKey)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public QuestionDTO create(final QuestionDTO questionDTO) {
        QuestionEntity questionEntity = QuestionEntity.builder().question(questionDTO.getName()).description(questionDTO.getDescription()).build();
        QuestionEntity savedQuestionEntity = questionEntityRepository.save(questionEntity);
        QuestionDTO newQuestionDTO = DTOFactory.getQuestionDTO(savedQuestionEntity, surveyService.findById(questionDTO.getSurveyPk()).getPrimaryKey());
        dtoMapping.put(newQuestionDTO, savedQuestionEntity);
        return newQuestionDTO;
    }

    @Override
    public void remove(final QuestionDTO questionDTO) {
        questionEntityRepository.delete(dtoMapping.get(questionDTO));
        dtoMapping.remove(questionDTO);
    }

    @Override
    public QuestionDTO update(final QuestionDTO questionDTO) {
        if (questionDTO == null) {
            return null;
        }
        QuestionEntity questionEntity = dtoMapping.get(questionDTO);
        questionEntity.setName(questionDTO.getName());
        questionEntity.setDescription(questionDTO.getDescription());
        QuestionEntity updatedEntity = questionEntityRepository.save(questionEntity);
        dtoMapping.replace(questionDTO, updatedEntity);
        return questionDTO;

    }

    @Override
    public Collection<QuestionDTO> findAllBySurvey(final SurveyDTO surveyDTO) {
        return dtoMapping.keySet().stream().filter(q -> q.getSurveyPk().equals(surveyDTO.getPrimaryKey())).toList();
    }

    private void load() {
        if (dtoMapping.isEmpty()) {
            List<QuestionEntity> all = questionEntityRepository.findAll();
            for (QuestionEntity questionEntity : all) {
                dtoMapping.put(DTOFactory.getQuestionDTO(questionEntity, surveyService.findById(questionEntity.getSurveyEntity().getId()).getPrimaryKey()), questionEntity);
            }
        }
    }

}
