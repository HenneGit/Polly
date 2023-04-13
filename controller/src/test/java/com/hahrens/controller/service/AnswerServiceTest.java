package com.hahrens.controller.service;

import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.service.AnswerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class AnswerServiceTest {

    private AnswerServiceImpl answerService;
    private UUID answerId;
    private String answerText;
    private TestSetup testSetup;


    @BeforeEach
    public void init() {
        testSetup = new TestSetup();
        answerService = new AnswerServiceImpl(testSetup.getDtoMapping());
        AnswerDTO answerDTO = answerService.findAll().stream().toList().get(0);
        answerId = answerDTO.getPrimaryKey();
        answerText = answerDTO.getAnswerText();
    }

    @Test
    public void testFindAll() {
        Collection<AnswerDTO> all = answerService.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    public void testFindById() {
        AnswerDTO byId = answerService.findById(answerId);
        AnswerDTO nullId = answerService.findById(null);
        assertNull(nullId);
        assertNotNull(byId);
        assertEquals(answerText, byId.getAnswerText());
    }

    @Test
    public void testRemove() {
        AnswerDTO byId = answerService.findById(answerId);
        assertNotNull(byId);
        answerService.delete(byId);
        AnswerDTO nullDto = answerService.findById(answerId);
        assertNull(nullDto);
    }

    @Test
    public void testCreate() {
        String answerText = "New Answer";
        AnswerDTO byId = answerService.findById(answerId);
        AnswerDTO answerDTO = new AnswerDTOImpl(null, byId.getQuestionPk(), answerText);
        answerService.create(answerDTO);
        Collection<AnswerDTO> all = answerService.findAll();
        assertNotNull(all);
        assertEquals(5, all.size());
        AnswerDTO createdAnswerDTO = all.stream().filter(a -> answerText.equals(a.getAnswerText())).findFirst().orElse(null);
        assertNotNull(createdAnswerDTO);
        assertNotNull(createdAnswerDTO.getPrimaryKey());
        assertEquals(byId.getQuestionPk(), createdAnswerDTO.getQuestionPk());
        AnswerDTO nullDTO = answerService.create(null);
        assertNull(nullDTO);
        testSetup.resetDtoMapping(answerService);
        answerService = new AnswerServiceImpl(testSetup.getDtoMapping());
        AnswerDTO byId1 = answerService.findAll().stream().filter(a -> answerText.equals(a.getAnswerText())).findFirst().orElse(null);
        assertNotNull(byId1);

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
        testSetup.resetDtoMapping(answerService);
        answerService = new AnswerServiceImpl(testSetup.getDtoMapping());
        AnswerDTO byId1 = answerService.findAll().stream().filter(a -> newAnswerText.equals(a.getAnswerText())).findFirst().orElse(null);
        assertNotNull(byId1);
        assertEquals(newAnswerText, byId1.getAnswerText());

    }

    @Test
    public void findAllByQuestion() {
//        List<QuestionDTO> questions = getQuestions();
//        Collection<AnswerDTO> allByQuestion = answerService.findAllByQuestion(questions.get(0));
//        Collection<AnswerDTO> allByQuestion2 = answerService.findAllByQuestion(questions.get(1));
//        assertEquals(0, allByQuestion.size());
//        assertEquals(1, allByQuestion2.size());

    }



}