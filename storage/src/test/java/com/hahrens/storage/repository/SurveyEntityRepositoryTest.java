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
public class SurveyEntityRepositoryTest {

    @Autowired
    private SurveyEntityRepository entityRepository;

    @Autowired
    private QuestionEntityRepository questionEntityRepository;

    @Autowired
    private AnswerEntityRepository answerEntityRepository;

    private static final String NAME = "Survey1";
    private static final String DESCRIPTION = "Description";

    @BeforeEach
    public void setup() {
        SurveyEntity surveyEntity = new SurveyEntity(null,NAME, DESCRIPTION, null);
        entityRepository.save(surveyEntity);
    }

    @Test
    public void testFindAll() {
        SurveyEntity surveyEntity = getSingleEntity();
        assertEquals(NAME, surveyEntity.getName());
        assertEquals(DESCRIPTION, surveyEntity.getDescription());
        assertNotNull(surveyEntity.getId());

    }

    @Test
    public void testCreate() {
        String name = "Name";
        String desc = "Description";
        SurveyEntity surveyEntity = new SurveyEntity(null, name, desc, null);
        entityRepository.save(surveyEntity);
        List<SurveyEntity> all = entityRepository.findAll();
        assertEquals(2, all.size());
        List<SurveyEntity> surveyEntities = all.stream().filter(s -> s.getName().equals(name)).toList();
        assertEquals(1, surveyEntities.size());
        SurveyEntity surveyEntity1 = surveyEntities.get(0);
        assertEquals(name, surveyEntity1.getName());
        assertEquals(desc, surveyEntity1.getDescription());
        assertNotNull(surveyEntity1.getId());

    }

    @Test
    public void testDelete() {
        entityRepository.delete(getSingleEntity());
        List<SurveyEntity> empty = entityRepository.findAll();
        assertEquals(0, empty.size());
    }

    @Test
    public void testUpdate() {
        SurveyEntity singleEntity = getSingleEntity();
        String newName = "New Name";
        String newDesc = "New Desc";
        singleEntity.setName(newName);
        singleEntity.setDescription(newDesc);
        entityRepository.save(singleEntity);
        SurveyEntity updatedEntity = getSingleEntity();
        assertEquals(newName, updatedEntity.getName());
        assertEquals(newDesc, updatedEntity.getDescription());
    }

    @Test
    public void testCascadingDelete() {
        SurveyEntity surveyEntity = getSingleEntity();
        QuestionEntity question = QuestionEntity.builder().question("Question").description("Dies das").surveyEntity(surveyEntity).build();
        questionEntityRepository.save(question);
        AnswerEntity answerEntity = AnswerEntity.builder().answerText("Test1").questionEntity(question).build();
        answerEntityRepository.save(answerEntity);
        question.addAnswer(answerEntity);
        surveyEntity.addQuestion(question);
        Optional<QuestionEntity> byId = questionEntityRepository.findById(question.getId());
        assertTrue(byId.isPresent());
        assertEquals("Test1", byId.get().getAnswers().get(0).getAnswerText());
        assertEquals("Test1", surveyEntity.getQuestionEntities().get(0).getAnswers().get(0).getAnswerText());
        entityRepository.delete(surveyEntity);
        assertTrue(questionEntityRepository.findAll().isEmpty());
        assertTrue(answerEntityRepository.findAll().isEmpty());
    }


    private SurveyEntity getSingleEntity() {
        List<SurveyEntity> all = entityRepository.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        return all.get(0);
    }


}
