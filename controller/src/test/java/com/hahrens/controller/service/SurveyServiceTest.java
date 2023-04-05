package com.hahrens.controller.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.api.service.QuestionService;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import com.hahrens.controller.implementation.service.AnswerServiceImpl;
import com.hahrens.controller.implementation.service.QuestionServiceImpl;
import com.hahrens.controller.implementation.service.SurveyServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class SurveyServiceTest {


    private AnswerService answerService;
    private QuestionService questionService;
    private SurveyService surveyService;
    private TestSetup testSetup;
    private Comparable<?> surveyPk;


    @BeforeEach
    public void init() {
        testSetup = new TestSetup();
        surveyService = new SurveyServiceImpl(testSetup.getDtoMapping());
        answerService = new AnswerServiceImpl(testSetup.getDtoMapping());
        questionService = new QuestionServiceImpl(testSetup.getDtoMapping());
        SurveyDTO surveyDTO = surveyService.findAll().stream().findFirst().orElse(null);
        assertNotNull(surveyDTO);
        surveyPk = surveyDTO.getPrimaryKey();


    }

    @Test
    public void testFindById() {
        SurveyDTO byId = surveyService.findById(surveyPk);
        assertNotNull(byId);
        assertNotNull(byId.getName());
        assertNotNull(byId.getDescription());
    }

    @Test
    public void testFindAll() {
        testCollectionSize(2);

    }

    @Test
    public void testRemove() {
        Collection<QuestionDTO> allBySurvey = questionService.findAllBySurveyId(surveyPk);
        assertNotNull(allBySurvey);
        assertFalse(allBySurvey.isEmpty());
        for (QuestionDTO questionDTO : allBySurvey) {
            Collection<AnswerDTO> allByQuestion = answerService.findAllByQuestionId(questionDTO.getPrimaryKey());
            if (!allByQuestion.isEmpty()) {
                allByQuestion.forEach(answerService::delete);
            }
        }
        answerService.save();
        allBySurvey.forEach(questionService::delete);
        questionService.save();
        surveyService.delete(surveyService.findById(surveyPk));
        testCollectionSize(1);
        resetServiceAndMapping();
        testCollectionSize(1);

    }

    private void resetServiceAndMapping() {
        testSetup.resetDtoMapping(surveyService);
        surveyService = new SurveyServiceImpl(testSetup.getDtoMapping());
    }

    @Test
    public void testCreate() {
        String description = "My second survey";
        String name = "New Survey";
        SurveyDTO surveyDTO = new SurveyDTOImpl(null, name, description);
        surveyService.create(surveyDTO);
        testPropertiesAfterUpdate(description, name);
        testCollectionSize(3);
        resetServiceAndMapping();
        testCollectionSize(3);
        testPropertiesAfterUpdate(description, name);
    }

    @Test
    public void testUpdate() {
        String description = "New Survey description";
        String name = "New Survey name";
        SurveyDTO byId = surveyService.findById(surveyPk);
        SurveyDTO surveyDTO = new SurveyDTOImpl(byId.getPrimaryKey(), name, description);
        surveyService.update(surveyDTO);
        testPropertiesAfterUpdate(description, name);
        resetServiceAndMapping();
        testPropertiesAfterUpdate(description, name);

    }


    private void testPropertiesAfterUpdate(String description, String name) {
        SurveyDTO updatedDto = surveyService.findAll().stream().filter(s -> s.getDescription().equals(description)).findFirst().orElse(null);
        assertNotNull(updatedDto);
        assertNotNull(updatedDto.getPrimaryKey());
        assertEquals(description, updatedDto.getDescription());
        assertEquals(name, updatedDto.getName());
    }


    private void testCollectionSize(int expected) {
        Collection<SurveyDTO> all = surveyService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertEquals(expected, all.size());
    }


}
