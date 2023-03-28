package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.backend.repository.SurveyEntityRepository;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.api.service.SurveyService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SurveyServiceImpl implements SurveyService {

    private SurveyEntityRepository surveyEntityRepository;

    private Map<SurveyDTO, SurveyEntity> dtoMapping;


    public SurveyServiceImpl(SurveyEntityRepository surveyEntityRepository) {
        this.surveyEntityRepository = surveyEntityRepository;
        dtoMapping = new HashMap<>();
    }


    @PreDestroy
    private void flush() {
        surveyEntityRepository.flush();
    }


    @Override
    public Collection<SurveyDTO> findAll() {
        load();
        return dtoMapping.keySet();
    }

    @Override
    public SurveyDTO findById(final Comparable<?> pk) {
        Set<Map.Entry<SurveyDTO, SurveyEntity>> entries = dtoMapping.entrySet();
        for (Map.Entry<SurveyDTO, SurveyEntity> entry : entries) {
            if (entry.getKey().getPrimaryKey().equals(pk)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public SurveyDTO create(final SurveyDTO surveyDTO) {
        if (surveyDTO == null) {
            return null;
        }
        SurveyEntity surveyEntity = SurveyEntity.builder().name(surveyDTO.getName()).description(surveyDTO.getDescription()).build();
        SurveyEntity saveQuestionEntity = surveyEntityRepository.save(surveyEntity);
        SurveyDTO newSurveyDTO = DTOFactory.getSurveyDTO(saveQuestionEntity);
        dtoMapping.put(newSurveyDTO, saveQuestionEntity);
        return newSurveyDTO;
    }

    @Override
    public void remove(final SurveyDTO surveyDTO) {
        if (surveyDTO == null) {
            return;
        }
        SurveyEntity surveyEntity = dtoMapping.get(surveyDTO);
        if (surveyEntity != null) {
            surveyEntityRepository.delete(surveyEntity);
        }
    }

    @Override
    public SurveyDTO update(final SurveyDTO surveyDTO) {
        if (surveyDTO == null) {
            return null;
        }
        SurveyEntity surveyEntity = dtoMapping.get(surveyDTO);
        surveyEntity.setName(surveyDTO.getName());
        surveyEntity.setDescription(surveyDTO.getDescription());
        SurveyEntity updatedEntity = surveyEntityRepository.save(surveyEntity);
        dtoMapping.replace(surveyDTO, updatedEntity);
        return surveyDTO;
    }

    private void load() {
        if (dtoMapping.isEmpty()) {
            List<SurveyEntity> all = surveyEntityRepository.findAll();
            for (SurveyEntity surveyEntity : all) {
                dtoMapping.put(DTOFactory.getSurveyDTO(surveyEntity), surveyEntity);
            }
        }
    }

//    AnswerEntity answerYes = AnswerEntity.builder().answerText("Yes2").build();
//    AnswerEntity answerNo = AnswerEntity.builder().answerText("No2").build();
//    QuestionEntity questionEntity = QuestionEntity.builder().name("Question 2").question("Had a nice day?").description("Question 1 if you had a nice day").answers(List.of(answerNo, answerYes)).build();
//    SurveyEntity surveyEntity = saveSurveyEntity(SurveyEntity.builder().name("My first survey 3").description("A simple test survey").questionEntities(List.of(questionEntity)).build());
//        answerNo.setQuestionEntity(questionEntity);
//        answerYes.setQuestionEntity(questionEntity);
//        questionEntity.setSurveyEntity(surveyEntity);
//    saveQuestionEntity(questionEntity);
//    saveAnswerEntity(answerYes);
//    saveAnswerEntity(answerNo);
//    List<SurveyEntity> all = surveyEntityRepository.findAll();

}
