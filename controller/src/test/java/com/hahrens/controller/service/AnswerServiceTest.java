package com.hahrens.controller.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.service.AnswerServiceImpl;
import com.hahrens.controller.implementation.service.DTOMappingImpl;
import com.hahrens.controller.service.mocks.AnswerEntityRepositoryMock;
import com.hahrens.controller.service.mocks.QuestionEntityRepositoryMock;
import com.hahrens.controller.service.mocks.SurveyEntityRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class AnswerServiceTest {

    private AnswerServiceImpl answerService;
    private DTOMappingImpl dtoMapping;
    private Comparable<?> answerId;
    private String answerText;

    @BeforeEach
    public void init() {
        SurveyEntityRepositoryMock surveyEntityRepositoryMock = new SurveyEntityRepositoryMock();
        List<SurveyEntity> all = surveyEntityRepositoryMock.findAll();
        QuestionEntityRepositoryMock questionEntityRepositoryMock = new QuestionEntityRepositoryMock(all.get(0), all.get(1));
        List<QuestionEntity> all1 = questionEntityRepositoryMock.findAll();
        AnswerEntityRepositoryMock answerEntityRepositoryMock = new AnswerEntityRepositoryMock(all1.get(0), all1.get(1));
        dtoMapping = new DTOMappingImpl(questionEntityRepositoryMock, answerEntityRepositoryMock, surveyEntityRepositoryMock);
        answerService = new AnswerServiceImpl(dtoMapping);
        List<AnswerEntity> allAnswers = answerEntityRepositoryMock.findAll();
        questionEntityRepositoryMock.setAnswers(allAnswers);
        List<QuestionEntity> allQuestions = questionEntityRepositoryMock.findAll();
        surveyEntityRepositoryMock.setQuestions(allQuestions);
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
        answerService.remove(byId);
        AnswerDTO nullDto = answerService.findById(answerId);
        assertNull(nullDto);
        resetDtoMapping();
        AnswerDTO stillNullDto = answerService.findById(answerId);
        assertNull(stillNullDto);
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
        resetDtoMapping();
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
        resetDtoMapping();
        AnswerDTO byId1 = answerService.findAll().stream().filter(a -> newAnswerText.equals(a.getAnswerText())).findFirst().orElse(null);
        assertNotNull(byId1);

    }

    @Test
    public void findAllByQuestion() {
//        List<QuestionDTO> questions = getQuestions();
//        Collection<AnswerDTO> allByQuestion = answerService.findAllByQuestion(questions.get(0));
//        Collection<AnswerDTO> allByQuestion2 = answerService.findAllByQuestion(questions.get(1));
//        assertEquals(0, allByQuestion.size());
//        assertEquals(1, allByQuestion2.size());

    }

    private void resetDtoMapping() {
        answerService.persistChanges();
        dtoMapping.init();
        answerService = new AnswerServiceImpl(dtoMapping);

    }

}