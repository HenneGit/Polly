package com.hahrens.controller.service;

import com.hahrens.backend.TestEntity;
import com.hahrens.backend.TestEntityRepository;
import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.backend.repository.AnswerEntityRepository;
import com.hahrens.backend.repository.QuestionEntityRepository;
import com.hahrens.backend.repository.SurveyEntityRepository;
import com.hahrens.controller.service.api.TestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestControllerImpl implements TestController {

    @Autowired
    private AnswerEntityRepository answerEntityRepository;

    @Autowired
    private QuestionEntityRepository questionEntityRepository;

    @Autowired
    private SurveyEntityRepository surveyEntityRepository;


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


    private TestEntityDTO toTestEntityDTO(final TestEntity testEntity) {
        return new TestEntityDTO(testEntity.getId(), testEntity.getName(), testEntity.getFirstName());
    }
}
