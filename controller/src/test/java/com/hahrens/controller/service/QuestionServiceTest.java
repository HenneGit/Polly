package com.hahrens.controller.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.service.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionServiceTest{

    private QuestionService questionService;
    private TestSetup testSetup;
    private Comparable<?> questionPk;
    private Comparable<?> surveyPk;

    @BeforeEach
    public void init() {
        testSetup = new TestSetup();
        questionService = new QuestionServiceImpl(testSetup.getDtoMapping());
        List<QuestionDTO> all = questionService.findAll().stream().toList();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        QuestionDTO questionDTO = all.get(0);
        questionPk = questionDTO.getPrimaryKey();
        surveyPk = questionDTO.getSurveyPk();
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
        assertEquals(3, all.size());
    }

    @Test
    public void testCreate() {
        String name = "Question2";
        String description2 = "Description2";
        String question = "Is Kermit a frog?";
        QuestionDTO questionDTO = new QuestionDTOImpl(null, name, description2, question,null, surveyPk);
        QuestionDTO newQuestionDTO = questionService.create(questionDTO);
        QuestionDTO byId = questionService.findById(newQuestionDTO.getPrimaryKey());
        assertNotNull(byId);
        assertEquals(name, byId.getName());
        assertEquals(description2, byId.getDescription());
        Collection<QuestionDTO> all = questionService.findAll();
        assertNotNull(all);
        assertEquals(5, all.size());
        testSetup.resetDtoMapping(questionService);
        questionService = new QuestionServiceImpl(testSetup.getDtoMapping());
        Collection<QuestionDTO> all2 = questionService.findAll();
        assertNotNull(all);
        assertEquals(5, all2.size());
    }

    @Test
    public void testUpdate() {
        QuestionDTO byId = questionService.findById(questionPk);
        String newName = "New Name";
        String newQuestion = "New Question";
        String newDescription = "New Description";
        QuestionDTO updatedQuestionDto = new QuestionDTOImpl(byId.getPrimaryKey(), newName, newDescription, newQuestion, byId.getAnswerContainer(), byId.getSurveyPk());
        questionService.update(updatedQuestionDto);
        QuestionDTO updatedDto = questionService.findById(questionPk);
        testPropertiesAfterUpdate(updatedDto, newName, newDescription, newQuestion);
        testSetup.resetDtoMapping(questionService);
        questionService = new QuestionServiceImpl(testSetup.getDtoMapping());
        QuestionDTO updatedDtoAfterPersist = questionService.findAll().stream().filter(q -> q.getName().equals(newName)).findFirst().orElse(null);
        testPropertiesAfterUpdate(updatedDtoAfterPersist, newName, newDescription, newQuestion);
    }

    private void testPropertiesAfterUpdate(QuestionDTO updatedDtoAfterPersist, String newName, String newDescription, String newQuestion) {
        assertNotNull(updatedDtoAfterPersist);
        assertEquals(newName, updatedDtoAfterPersist.getName());
        assertEquals(newDescription, updatedDtoAfterPersist.getDescription());
        assertEquals(newQuestion, updatedDtoAfterPersist.getQuestion());
    }


    private void testProperties(final QuestionDTO questionDTO) {
        assertNotNull(questionDTO);
        Comparable<?> primaryKey = questionDTO.getPrimaryKey();
        assertEquals(primaryKey, questionPk);
    }


}

