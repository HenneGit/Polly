package com.hahrens.controller.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.repository.AnswerEntityRepository;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.service.AnswerServiceImpl;
import com.hahrens.controller.service.mocks.QuestionServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    private AnswerService answerService;

    private static final Long QUESTION_ID = Long.valueOf(99);
    private static final Long QUESTION_ID_2 = Long.valueOf(199);
    private static final Long ANSWER_ID = Long.valueOf(999);
    private Comparable<?> MAPPED_ANSWER_ID;
    private static final String ANSWER_TEXT = "Answer Text";

    @BeforeEach
    public void init() {
        QuestionServiceMock questionServiceMock = new QuestionServiceMock(getQuestions());
        List<AnswerEntity> answerEntities = new ArrayList<>();
        QuestionEntity questionEntity = QuestionEntity.builder().id(QUESTION_ID).build();
        AnswerEntity oneAnswer = AnswerEntity.builder().answerText(ANSWER_TEXT).id(ANSWER_ID).questionEntity(questionEntity).build();
        answerEntities.add(oneAnswer);
        AnswerEntityRepositoryMock answerEntityRepositoryMock = new AnswerEntityRepositoryMock(answerEntities);
        answerService = new AnswerServiceImpl(answerEntityRepositoryMock, questionServiceMock);
        Collection<AnswerDTO> all = answerService.findAll();
        List<AnswerDTO> answerDTOS = all.stream().toList();
        MAPPED_ANSWER_ID = answerDTOS.get(0).getPrimaryKey();
    }

    @Test
    public void testFindAll() {
        Collection<AnswerDTO> all = answerService.findAll();
        List<AnswerDTO> answerDTOS = all.stream().toList();
        assertFalse(all.isEmpty());
        assertEquals(ANSWER_TEXT, answerDTOS.get(0).getAnswerText());
    }

    @Test
    public void testFindById() {
        AnswerDTO byId = answerService.findById(MAPPED_ANSWER_ID);
        AnswerDTO nullId = answerService.findById(null);
        assertNull(nullId);
        assertNotNull(byId);
        assertEquals(ANSWER_TEXT, byId.getAnswerText());
    }

    @Test
    public void testRemove() {
        AnswerDTO byId = answerService.findById(MAPPED_ANSWER_ID);
        assertNotNull(byId);
        answerService.remove(byId);
        AnswerDTO nullDto = answerService.findById(MAPPED_ANSWER_ID);
        assertNull(nullDto);
    }

    @Test
    public void testCreate() {
        String answerText = "New Answer";
        AnswerDTO answerDTO = new AnswerDTOImpl(null, QUESTION_ID, answerText);
        answerService.create(answerDTO);
        Collection<AnswerDTO> all = answerService.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());
        AnswerDTO createdAnswerDTO = all.stream().filter(a -> answerText.equals(a.getAnswerText())).findFirst().get();
        assertNotNull(createdAnswerDTO);
        assertNotNull(createdAnswerDTO.getPrimaryKey());
        assertEquals(QUESTION_ID, createdAnswerDTO.getQuestionPk());

        AnswerDTO nullDTO = answerService.create(null);
        assertNull(nullDTO);

    }

    @Test
    public void testUpdate() {
        AnswerDTO nullUpdate = answerService.update(null);
        assertNull(nullUpdate);
        AnswerDTO wrongDTO = new AnswerDTOImpl(UUID.randomUUID(), UUID.randomUUID(), "Not a real DTO");
        AnswerDTO nullUpdate2 = answerService.update(wrongDTO);
        assertNull(nullUpdate2);
        String newAnswerText = "New answer text";
        AnswerDTO answerDTO = answerService.findAll().stream().findFirst().get();
        AnswerDTO DTOtoUpdate = new AnswerDTOImpl(answerDTO.getPrimaryKey(), answerDTO.getQuestionPk(), newAnswerText);
        AnswerDTO updatedDTO = answerService.update(DTOtoUpdate);
        assertNotNull(updatedDTO);
        assertEquals(newAnswerText, updatedDTO.getAnswerText());
    }

    @Test
    public void findAllByQuestion() {
        List<QuestionDTO> questions = getQuestions();
        Collection<AnswerDTO> allByQuestion = answerService.findAllByQuestion(questions.get(0));
        Collection<AnswerDTO> allByQuestion2 = answerService.findAllByQuestion(questions.get(1));
        assertEquals(0, allByQuestion.size());
        assertEquals(1, allByQuestion2.size());

    }


    private List<QuestionDTO> getQuestions() {
        QuestionDTO questionDTO = new QuestionDTOImpl(QUESTION_ID,"Question1", "First Question",null, UUID.randomUUID());
        QuestionDTO questionDTO2 = new QuestionDTOImpl(QUESTION_ID_2,"Question2", "Second Question",null, UUID.randomUUID());
        return List.of(questionDTO2, questionDTO);

    }


    private static class AnswerEntityRepositoryMock implements AnswerEntityRepository {

        private List<AnswerEntity> entities;

        public AnswerEntityRepositoryMock(List<AnswerEntity> entities) {
            this.entities = entities;
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
            entity.setId(Long.valueOf(88));
            entities.add(entity);
            return entity;
        }

        @Override
        public <S extends AnswerEntity> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<AnswerEntity> findById(Long aLong) {
            return entities.stream().filter(a -> a.getId().equals(aLong)).findFirst();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public List<AnswerEntity> findAll() {
            return entities;
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


}
