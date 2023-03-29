package com.hahrens.controller.service.mocks;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.backend.repository.SurveyEntityRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class SurveyEntityRepositoryMock implements SurveyEntityRepository {



    Map<Long, SurveyEntity> surveyEntities;


    public SurveyEntityRepositoryMock() {
        surveyEntities = new HashMap<>();
        surveyEntities.put(TestConstants.SURVEY_1_ID, SurveyEntity.builder().name("Survey1").description(TestConstants.SURVEY_1_DESCRIPTION).id(TestConstants.SURVEY_1_ID).build());
        surveyEntities.put(TestConstants.SURVEY_2_ID, SurveyEntity.builder().name("Survey2").description(TestConstants.SURVEY_1_DESCRIPTION).id(TestConstants.SURVEY_2_ID).build());

    }

    private Long getRandomLong() {
        return new Random().nextLong();
    }

    public void setQuestions(List<QuestionEntity> questionEntities) {
        Set<Map.Entry<Long, SurveyEntity>> entries = surveyEntities.entrySet();
        for (QuestionEntity questionEntity : questionEntities) {
            for (Map.Entry<Long, SurveyEntity> entry : entries) {
                if (questionEntity.getSurveyEntity().getId().equals(entry.getKey())) {
                    entry.getValue().addQuestion(questionEntity);
                }
            }
        }
    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends SurveyEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SurveyEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<SurveyEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SurveyEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public SurveyEntity getById(Long aLong) {
        return surveyEntities.get(aLong);
    }

    @Override
    public SurveyEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SurveyEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SurveyEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends SurveyEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends SurveyEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SurveyEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SurveyEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SurveyEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends SurveyEntity> S save(S entity) {
        SurveyEntity surveyEntity = surveyEntities.get(entity.getId());
        if (surveyEntity != null) {
            surveyEntity.setName(entity.getName());
            surveyEntity.setDescription(entity.getDescription());
            return (S) surveyEntity;
        }
        Long randomLong = getRandomLong();
        entity.setId(randomLong);
        surveyEntities.put(randomLong, entity);
        return entity;
    }

    @Override
    public <S extends SurveyEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<SurveyEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<SurveyEntity> findAll() {
        return surveyEntities.values().stream().toList();
    }

    @Override
    public List<SurveyEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        surveyEntities.remove(aLong);
    }

    @Override
    public void delete(SurveyEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends SurveyEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SurveyEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SurveyEntity> findAll(Pageable pageable) {
        return null;
    }
}
