package com.hahrens.storage.repository;

import com.hahrens.storage.model.AnswerEntity;
import com.hahrens.storage.model.QuestionEntity;
import com.hahrens.storage.model.SurveyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AnswerEntityRepositoryTest {


    @Autowired
    private AnswerEntityRepository entityRepository;

    @Autowired
    private QuestionEntityRepository questionEntityRepository;

    @Autowired
    private SurveyEntityRepository surveyEntityRepository;

    private QuestionEntity questionEntity;
    private static final String ANSWER_TEXT = "Answer1";
    private static final String NAME2 = "Answer2";

    @BeforeEach
    public void setup() {
        SurveyEntity surveyEntity = SurveyEntity.builder().description("Desc").name("Name").build();
        surveyEntityRepository.save(surveyEntity);
        questionEntity = QuestionEntity.builder().surveyEntity(surveyEntity).question("Question1").description("desc").build();
        QuestionEntity saved = questionEntityRepository.saveAndFlush(questionEntity);
        AnswerEntity answerEntity = new AnswerEntity(null, ANSWER_TEXT, questionEntity);
        entityRepository.save(answerEntity);
        saved.addAnswer(answerEntity);
    }

    @Test
    public void testFindAll() {
        getSingleEntity();
    }

    @Test
    public void testCreate() {
        AnswerEntity answerEntity = new AnswerEntity(null, NAME2, questionEntity);
        entityRepository.save(answerEntity);
        List<AnswerEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        Optional<AnswerEntity> first = all.stream().filter(a -> NAME2.equals(a.getAnswerText())).findFirst();
        assertTrue(first.isPresent());
        AnswerEntity newAnswerEntity = first.get();
        assertEquals(questionEntity.getId(), newAnswerEntity.getQuestionEntity().getId());
        Optional<QuestionEntity> byId = questionEntityRepository.findById(questionEntity.getId());
        assertTrue(byId.isPresent());
        QuestionEntity questionEntity = byId.get();
        questionEntity.addAnswer(answerEntity);
        Optional<QuestionEntity> byIdNew = questionEntityRepository.findById(questionEntity.getId());
        assertTrue(byIdNew.isPresent());
        List<AnswerEntity> answers = byIdNew.get().getAnswers();
        assertFalse(answers.isEmpty());
        assertEquals(2, answers.size());

    }
    @Test
    public void testFindById() {
        AnswerEntity singleEntity = getSingleEntity();
        Optional<AnswerEntity> byId = entityRepository.findById(singleEntity.getId());
        assertTrue(byId.isPresent());
        AnswerEntity answerEntity = byId.get();
        assertEquals(ANSWER_TEXT, answerEntity.getAnswerText());
    }

    @Test
    public void testDelete() {
        AnswerEntity singleEntity = getSingleEntity();
        Optional<QuestionEntity> byId = questionEntityRepository.findById(singleEntity.getQuestionEntity().getId());
        assertTrue(byId.isPresent());
        byId.get().removeAnswer(singleEntity);
        entityRepository.delete(singleEntity);
        List<AnswerEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    public void testUpdate() {
        AnswerEntity singleEntity = getSingleEntity();
        String newAnswerText = "New Answer Text";
        singleEntity.setAnswerText(newAnswerText);
        assertEquals(newAnswerText, getSingleEntity().getAnswerText());

    }

    private AnswerEntity getSingleEntity() {
        List<AnswerEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertEquals(1, all.size());
        return all.get(0);
    }

}
