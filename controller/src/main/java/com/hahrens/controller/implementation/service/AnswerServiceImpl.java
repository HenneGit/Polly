package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.repository.AnswerEntityRepository;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.api.service.QuestionService;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnswerServiceImpl implements AnswerService {

    private AnswerEntityRepository answerEntityRepository;

    private QuestionService questionService;

    private Map<AnswerDTO, AnswerEntity> dtoMapping;

    public AnswerServiceImpl(AnswerEntityRepository answerEntityRepository, QuestionService questionService) {
        this.answerEntityRepository = answerEntityRepository;
        this.questionService = questionService;
        dtoMapping = new HashMap<>();

    }

    @PreDestroy
    private void flush() {
        answerEntityRepository.flush();
    }

    @Override
    public Collection<AnswerDTO> findAll() {
        if (dtoMapping.isEmpty()) {
            List<AnswerEntity> all = answerEntityRepository.findAll();
            for (AnswerEntity answerEntity : all) {
                dtoMapping.put(DTOFactory.getAnswerDTO(answerEntity, questionService.findById(answerEntity.getQuestionEntity().getId()).getPrimaryKey()), answerEntity);
            }
        }
        return dtoMapping.keySet();
    }

    @Override
    public AnswerDTO findById(final Comparable<?> primaryKey) {
        if (primaryKey == null) {
            return null;
        }
        Set<Map.Entry<AnswerDTO, AnswerEntity>> entries = dtoMapping.entrySet();
        for (Map.Entry<AnswerDTO, AnswerEntity> entry : entries) {
            if (entry.getKey().getPrimaryKey().equals(primaryKey)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public AnswerDTO create(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        AnswerEntity answerEntity = AnswerEntity.builder().answerText(answerDTO.getAnswerText()).build();
        AnswerEntity saved = answerEntityRepository.save(answerEntity);
        dtoMapping.put(DTOFactory.getAnswerDTO(saved, answerDTO.getQuestionPk()), answerEntity);
        return answerDTO;
    }

    @Override
    public void remove(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return;
        }
        AnswerEntity entity = dtoMapping.get(answerDTO);
        if (entity == null) {
            return;
        }
        answerEntityRepository.delete(entity);
        dtoMapping.remove(answerDTO);
    }

    @Override
    public AnswerDTO update(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        Optional<AnswerDTO> optionalAnswerDTO = dtoMapping.keySet().stream().filter(dto -> dto.getPrimaryKey().equals(answerDTO.getPrimaryKey())).findFirst();
        if (optionalAnswerDTO.isEmpty()) {
            return null;
        }
        AnswerEntity answerEntity = dtoMapping.get(optionalAnswerDTO.get());
        answerEntity.setAnswerText(answerDTO.getAnswerText());
        AnswerEntity updatedEntity = answerEntityRepository.save(answerEntity);
        dtoMapping.replace(answerDTO, updatedEntity);
        return answerDTO;
    }

    @Override
    public Collection<AnswerDTO> findAllByQuestion(final QuestionDTO questionDTO) {
       return dtoMapping.keySet().stream().filter(a -> a.getQuestionPk().equals(questionDTO.getPrimaryKey())).toList();
    }
}
