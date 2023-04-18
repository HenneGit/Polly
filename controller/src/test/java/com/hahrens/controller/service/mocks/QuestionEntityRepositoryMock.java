package com.hahrens.controller.service.mocks;

import com.hahrens.storage.model.AnswerEntity;
import com.hahrens.storage.model.QuestionEntity;
import com.hahrens.storage.model.SurveyEntity;
import com.hahrens.storage.repository.QuestionEntityRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class QuestionEntityRepositoryMock implements QuestionEntityRepository {


    Map<Long, QuestionEntity> questionEntities;


    public QuestionEntityRepositoryMock(SurveyEntity surveyEntity1, SurveyEntity surveyEntity2) {
        questionEntities = new HashMap<>();
        questionEntities.put(TestConstants.QUESTION_1_ID, QuestionEntity.builder().name(TestConstants.QUESTION_1) //
                .description(TestConstants.QUESTION_1_DESCRIPTION) //
                .question(TestConstants.QUESTION_1_QUESTION) //
                .surveyEntity(surveyEntity1) //
                .id(TestConstants.QUESTION_1_ID).build()); //
        questionEntities.put(TestConstants.QUESTION_2_ID, QuestionEntity.builder().name(TestConstants.QUESTION_2) //
                .description("Cloudy Question") //
                .question("Is it cloudy.") //
                .surveyEntity(surveyEntity1) //
                .id(TestConstants.QUESTION_2_ID).build());
        questionEntities.put(TestConstants.QUESTION_3_ID, QuestionEntity.builder().name(TestConstants.QUESTION_3) //
                .description("Rainy Question") //
                .question("Is it raining") //
                .surveyEntity(surveyEntity2) //
                .id(TestConstants.QUESTION_3_ID).build());
        questionEntities.put(TestConstants.QUESTION_4_ID, QuestionEntity.builder().name(TestConstants.QUESTION_4)
                .description("Windy Question") //
                .question("Is it windy") //
                .surveyEntity(surveyEntity2) //
                .id(TestConstants.QUESTION_4_ID).build());
    }

    private Long getRandomLong() {
        return new Random().nextLong();
    }

    public void setAnswers(List<AnswerEntity> answerEntities) {
        Set<Map.Entry<Long, QuestionEntity>> entries = questionEntities.entrySet();
        for (AnswerEntity answerEntity : answerEntities) {
            for (Map.Entry<Long, QuestionEntity> entry : entries) {
                if (answerEntity.getQuestionEntity().getId().equals(entry.getKey())) {
                    entry.getValue().addAnswer(answerEntity);
                }
            }
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends QuestionEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends QuestionEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<QuestionEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public QuestionEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public QuestionEntity getById(Long aLong) {
        return questionEntities.get(aLong);
    }

    @Override
    public QuestionEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends QuestionEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends QuestionEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends QuestionEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends QuestionEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends QuestionEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends QuestionEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends QuestionEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends QuestionEntity> S save(S entity) {
        QuestionEntity questionEntity = questionEntities.get(entity.getId());
        if (questionEntity != null) {
            questionEntity.setQuestion(entity.getQuestion());
            questionEntity.setName(entity.getName());
            questionEntity.setDescription(entity.getDescription());
            return (S) questionEntity;
        }
        Long randomLong = getRandomLong();
        entity.setId(randomLong);
        questionEntities.put(randomLong, entity);
        return entity;
    }

    @Override
    public <S extends QuestionEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<QuestionEntity> findById(Long aLong) {
        return Optional.ofNullable(questionEntities.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<QuestionEntity> findAll() {
        return questionEntities.values().stream().toList();
    }

    @Override
    public List<QuestionEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        questionEntities.remove(aLong);
    }

    @Override
    public void delete(QuestionEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends QuestionEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<QuestionEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<QuestionEntity> findAll(Pageable pageable) {
        return null;
    }
}
