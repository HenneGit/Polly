package com.hahrens.controller.service.mocks;

import com.hahrens.storage.model.AnswerEntity;
import com.hahrens.storage.model.QuestionEntity;
import com.hahrens.storage.repository.AnswerEntityRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class AnswerEntityRepositoryMock implements AnswerEntityRepository {

    Map<Long, AnswerEntity> answerEntities;


    public AnswerEntityRepositoryMock(QuestionEntity questionEntity1, QuestionEntity questionEntity2) {
        answerEntities = new HashMap<>();
        answerEntities.put(TestConstants.ANSWER_1_ID, AnswerEntity.builder().answerText(TestConstants.ANSWER_1).questionEntity(questionEntity1).id(TestConstants.ANSWER_1_ID).build());
        answerEntities.put(TestConstants.ANSWER_2_ID, AnswerEntity.builder().answerText(TestConstants.ANSWER_2).questionEntity(questionEntity1).id(TestConstants.ANSWER_2_ID).build());
        answerEntities.put(TestConstants.ANSWER_3_ID, AnswerEntity.builder().answerText(TestConstants.ANSWER_3).questionEntity(questionEntity2).id(TestConstants.ANSWER_3_ID).build());
        answerEntities.put(TestConstants.ANSWER_4_ID, AnswerEntity.builder().answerText(TestConstants.ANSWER_4).questionEntity(questionEntity2).id(TestConstants.ANSWER_4_ID).build());
    }

    private Long getRandomLong() {
        return new Random().nextLong();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AnswerEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AnswerEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<AnswerEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AnswerEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public AnswerEntity getById(Long aLong) {
        return null;
    }

    @Override
    public AnswerEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends AnswerEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AnswerEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AnswerEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AnswerEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AnswerEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AnswerEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AnswerEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends AnswerEntity> S save(S entity) {
        AnswerEntity answerEntity = answerEntities.get(entity.getId());
        if (answerEntity != null) {
            answerEntity.setAnswerText(entity.getAnswerText());
            return (S) answerEntity;
        }
        Long randomLong = getRandomLong();
        entity.setId(randomLong);
        answerEntities.put(randomLong, entity);
        return entity;
    }

    @Override
    public <S extends AnswerEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AnswerEntity> findById(Long aLong) {
        return Optional.of(answerEntities.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<AnswerEntity> findAll() {
        return answerEntities.values().stream().toList();
    }

    @Override
    public List<AnswerEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        answerEntities.remove(aLong);
    }

    @Override
    public void delete(AnswerEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends AnswerEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<AnswerEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AnswerEntity> findAll(Pageable pageable) {
        return null;
    }
}
