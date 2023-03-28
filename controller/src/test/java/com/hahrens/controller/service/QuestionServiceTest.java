package com.hahrens.controller.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.backend.repository.QuestionEntityRepository;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import com.hahrens.controller.implementation.service.QuestionServiceImpl;
import com.hahrens.controller.service.mocks.SurveyServiceMock;
import graphql.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionServiceTest {

    private QuestionService questionService;
    private static final Long SURVEY_ID = Long.valueOf(99);
    private static final Long SURVEY_ID_2 = Long.valueOf(199);
    private Comparable<?> MAPPED_QUESTION_ID;
    private static final String NAME = "Question 1";
    private static final String ANSWER_TEXT_1 = "Answer 1";
    private static final String ANSWER_TEXT_2 = "Answer 2";
    private static final String DESCRIPTION = "Question Text";
    private static Comparable<?> questionPk;

    @BeforeEach
    public void init() {
        SurveyEntity surveyEntity = SurveyEntity.builder().name("new Survey").id(SURVEY_ID).build();
        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        AnswerEntity answer1 = AnswerEntity.builder().id(Long.valueOf(new Random().nextLong())).answerText(ANSWER_TEXT_1).build();
        AnswerEntity answer2 = AnswerEntity.builder().id(Long.valueOf(new Random().nextLong())).answerText(ANSWER_TEXT_2).build();
        QuestionEntity questionEntity = QuestionEntity.builder().question(NAME).description(DESCRIPTION).answers(List.of(answer1, answer2)).surveyEntity(surveyEntity).build();
        QuestionEntity saved = questionRepositoryMock.save(questionEntity);
        SurveyDTO surveyDTO = new SurveyDTOImpl(SURVEY_ID, "New Survey", "A Survey", null);
        SurveyServiceMock surveyServiceMock = new SurveyServiceMock(List.of(surveyDTO));
        questionService = new QuestionServiceImpl(questionRepositoryMock, surveyServiceMock);
        QuestionDTO questionDTO = questionService.findAll().stream().findFirst().get();
        questionPk = questionDTO.getPrimaryKey();
    }

    @Test
    public void testFindAll() {
        Collection<QuestionDTO> all = questionService.findAll();
        assertNotNull(all);
        QuestionDTO questionDTO = all.stream().toList().get(0);
        testProperties(questionDTO);
    }

    @Test
    public void testFindById() {
        QuestionDTO byId = questionService.findById(questionPk);
        testProperties(byId);
    }

    @Test
    public void testRemove() {
        QuestionDTO byId = questionService.findById(questionPk);
        questionService.remove(byId);
        Collection<QuestionDTO> all = questionService.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    public void testCreate() {
        String name = "Question2";
        String description2 = "Description2";
        QuestionDTO questionDTO = new QuestionDTOImpl(null, name, description2, null, SURVEY_ID);
        QuestionDTO newQuestionDTO = questionService.create(questionDTO);
        QuestionDTO byId = questionService.findById(newQuestionDTO.getPrimaryKey());
        assertNotNull(byId);
        assertEquals(name, byId.getName());
        assertEquals(description2, byId.getDescription());
        Collection<QuestionDTO> all = questionService.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());

    }


    private void testProperties(QuestionDTO questionDTO) {
        assertNotNull(questionDTO);
        Comparable<?> surveyPk = questionDTO.getSurveyPk();
        Comparable<?> primaryKey = questionDTO.getPrimaryKey();
        String description = questionDTO.getDescription();
        String name = questionDTO.getName();
        assertEquals(surveyPk, SURVEY_ID);
        assertEquals(primaryKey, questionPk);
        assertEquals(description, questionDTO.getDescription());
        assertEquals(name, questionDTO.getName());
    }


    private static final class QuestionRepositoryMock implements QuestionEntityRepository {


        private List<QuestionEntity> entities;

        public QuestionRepositoryMock() {
            this.entities = new ArrayList<>();
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
            return null;
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
            long id = new Random().nextLong();
            entity.setId(Long.valueOf(id));
            entities.add(entity);
            return entity;
        }

        @Override
        public <S extends QuestionEntity> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<QuestionEntity> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public List<QuestionEntity> findAll() {
            return entities;
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

        }

        @Override
        public void delete(QuestionEntity entity) {
            entities.remove(entity);
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
}

