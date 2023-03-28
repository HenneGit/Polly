package com.hahrens.backend.repository;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuestionEntityRepositoryTest {

    @Autowired
    private QuestionEntityRepository entityRepository;

    @Autowired
    private AnswerEntityRepository answerEntityRepository;

    @Autowired
    private SurveyEntityRepository surveyEntityRepository;
    private static final String QUESTION = "question";
    private static final String NAME = "question1";
    private static final String DESCRIPTION = "Description";
    private SurveyEntity surveyEntity;

    @BeforeEach

    public void setup() {
        surveyEntity = new SurveyEntity(null, "Name", "Desc", null);
        surveyEntityRepository.save(surveyEntity);
        QuestionEntity questionEntity = new QuestionEntity(null, NAME, QUESTION, DESCRIPTION, null, surveyEntity);
        entityRepository.save(questionEntity);
        surveyEntity.addQuestion(questionEntity);
    }

    @Test
    public void testFindAll() {
        List<QuestionEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());

    }

    @Test
    public void testFindById() {
        QuestionEntity singleEntity = getSingleEntity();
        Optional<QuestionEntity> byId = entityRepository.findById(singleEntity.getId());
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        QuestionEntity questionEntity = byId.get();
        assertEquals(QUESTION, questionEntity.getQuestion());
        assertEquals(DESCRIPTION, questionEntity.getDescription());
        assertEquals(NAME, questionEntity.getName());

    }

    @Test
    public void testDelete() {
        QuestionEntity singleEntity = getSingleEntity();
        singleEntity.getSurveyEntity().removeQuestion(singleEntity);
        entityRepository.delete(singleEntity);
        List<QuestionEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    public void testUpdate() {
        QuestionEntity singleEntity = getSingleEntity();
        String newQuestion = "New Question";
        String newDescription = "New description";
        String newName = "New name";
        singleEntity.setName(newName);
        singleEntity.setDescription(newDescription);
        singleEntity.setQuestion(newQuestion);
        entityRepository.save(singleEntity);
        QuestionEntity updatedEntity = getSingleEntity();
        assertEquals(newQuestion, updatedEntity.getQuestion());
        assertEquals(newDescription, updatedEntity.getDescription());
        assertEquals(newName, updatedEntity.getName());

    }

    @Test
    public void testCreate() {
        String newQuestion = "New Question";
        String newDescription = "New description";
        String newName = "New name";
        QuestionEntity newEntity = QuestionEntity.builder().question(newQuestion).description(newDescription).name(newName).surveyEntity(surveyEntity).build();
        entityRepository.save(newEntity);
        List<QuestionEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertEquals(2, all.size());
        Optional<QuestionEntity> first = all.stream().filter(q -> newQuestion.equals(q.getQuestion())).findFirst();
        assertTrue(first.isPresent());
        QuestionEntity questionEntity = first.get();
        assertEquals(newQuestion, questionEntity.getQuestion());
        assertEquals(newName, questionEntity.getName());
        assertEquals(newDescription, questionEntity.getDescription());
        assertNotNull(questionEntity.getId());

    }

    @Test
    public void testCascadingDelete() {
        QuestionEntity singleEntity = getSingleEntity();
        singleEntity.getSurveyEntity().removeQuestion(singleEntity);
        AnswerEntity test = AnswerEntity.builder().answerText("test").questionEntity(singleEntity).build();
        AnswerEntity savedAnswer = answerEntityRepository.save(test);
        singleEntity.addAnswer(savedAnswer);
        entityRepository.delete(singleEntity);
        List<QuestionEntity> all = entityRepository.findAll();
        assertTrue(all.isEmpty());
        List<AnswerEntity> answers = answerEntityRepository.findAll();
        assertTrue(answers.isEmpty());
    }


    private QuestionEntity getSingleEntity() {
        List<QuestionEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        return all.get(0);
    }

}
