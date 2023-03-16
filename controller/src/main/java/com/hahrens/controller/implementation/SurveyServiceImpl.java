package com.hahrens.controller.implementation;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.backend.repository.AnswerEntityRepository;
import com.hahrens.backend.repository.QuestionEntityRepository;
import com.hahrens.backend.repository.SurveyEntityRepository;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import com.hahrens.controller.service.api.TestController;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SurveyServiceImpl implements SurveyService {

    private AnswerEntityRepository answerEntityRepository;

    private QuestionEntityRepository questionEntityRepository;

    private SurveyEntityRepository surveyEntityRepository;

    private Map<Comparable<?>, SurveyEntity> repository;

    public SurveyServiceImpl(AnswerEntityRepository answerEntityRepository, QuestionEntityRepository questionEntityRepository, SurveyEntityRepository surveyEntityRepository) {
        this.answerEntityRepository = answerEntityRepository;
        this.questionEntityRepository = questionEntityRepository;
        this.surveyEntityRepository = surveyEntityRepository;
        repository = new HashMap<>();
    }

    @Override
    public List<TestEntityDTO> findAll() {
        AnswerEntity answerYes = AnswerEntity.builder().answerText("Yes2").build();
        AnswerEntity answerNo = AnswerEntity.builder().answerText("No2").build();
        QuestionEntity questionEntity = QuestionEntity.builder().name("Question 2").question("Had a nice day?").description("Question 1 if you had a nice day").answers(List.of(answerNo, answerYes)).build();
        SurveyEntity surveyEntity = saveSurveyEntity(SurveyEntity.builder().name("My first survey 3").description("A simple test survey").questionEntities(List.of(questionEntity)).build());
        answerNo.setQuestionEntity(questionEntity);
        answerYes.setQuestionEntity(questionEntity);
        questionEntity.setSurveyEntity(surveyEntity);
        saveQuestionEntity(questionEntity);
        saveAnswerEntity(answerYes);
        saveAnswerEntity(answerNo);
        List<SurveyEntity> all = surveyEntityRepository.findAll();

        return null;
    }

    private AnswerEntity saveAnswerEntity(final AnswerEntity entity) {
        return answerEntityRepository.save(entity);
    }

    private QuestionEntity saveQuestionEntity(final QuestionEntity entity) {
        return questionEntityRepository.save(entity);
    }

    private SurveyEntity saveSurveyEntity(final SurveyEntity entity) {
        return surveyEntityRepository.save(entity);
    }


    @Override
    public SurveyDTO findById(Comparable<?> pk) {
        return null;
    }

    @Override
    public Collection<SurveyDTO> getSurveys() {
        return null;
    }

    private SurveyDTO toSurveyDTO(SurveyEntity surveyEntity) {
        repository.put(surveyEntity.getId(), surveyEntity);
        return new SurveyDTOImpl(surveyEntity.)


    }

}
