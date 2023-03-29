package com.hahrens.controller.service;

import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionServiceTest{

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
        String question = "Is Kermit a frog?";
        QuestionDTO questionDTO = new QuestionDTOImpl(null, name, description2, question,null, SURVEY_ID);
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


}

