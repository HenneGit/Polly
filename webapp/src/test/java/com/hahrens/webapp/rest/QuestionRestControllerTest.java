package com.hahrens.webapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahrens.controller.api.service.dto.QuestionService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
import com.hahrens.webapp.rest.dto.QuestionRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class QuestionRestControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionRestController restController;

    private static final String QUESTION_ROUTE = "/question/";
    private MockMvc mvc;
    private UUID primaryKey;
    private UUID surveyPk;
    
    private JacksonTester<List<QuestionDTOImpl>> jsonListWriter;
    private JacksonTester<QuestionDTOImpl> questionDTOJacksonTester;
    private JacksonTester<SurveyDTOImpl> surveyDTOJacksonTester;
    private QuestionDTOImpl questionDTO;
    private SurveyDTOImpl surveyDTO;
    
    @BeforeEach
    public void init() {
        primaryKey = UUID.randomUUID();
        surveyPk = UUID.randomUUID();
        JacksonTester.initFields(this, new ObjectMapper());
        questionDTO = new QuestionDTOImpl(primaryKey, "name", "desc", "question", surveyPk, Integer.valueOf(10));
        surveyDTO = new SurveyDTOImpl(UUID.randomUUID(), "Survey", "Desc");
        lenient().when(questionService.findAll()).thenReturn(List.of(questionDTO));
        lenient().when(questionService.findById(primaryKey)).thenReturn(questionDTO);
        lenient().when(questionService.update(any(QuestionDTOImpl.class))).thenReturn(questionDTO);
        lenient().when(questionService.create(any(QuestionDTOImpl.class))).thenReturn(questionDTO);
        lenient().when(questionService.findAllBySurveyId(any(UUID.class))).thenReturn(List.of(questionDTO));
        mvc = MockMvcBuilders.standaloneSetup(restController)
                .build();
    }
    @Test
    public void testGetQuestions() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(QUESTION_ROUTE + "get").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String contentAsString = response.getContentAsString();
        assertEquals(jsonListWriter.parseObject(contentAsString).get(0).getQuestion(), List.of(questionDTO).get(0).getQuestion());
    }

    @Test
    public void testFindById() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(QUESTION_ROUTE + "getById/" +primaryKey).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), questionDTOJacksonTester.write(questionDTO).getJson());
        MockHttpServletResponse nullResponse = mvc.perform(get(QUESTION_ROUTE).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), nullResponse.getStatus());
        MockHttpServletResponse wrongIdResponse = mvc.perform(get(QUESTION_ROUTE + "asdasd").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), wrongIdResponse.getStatus());
    }

    @Test
    public void testDelete() throws Exception {
        MockHttpServletResponse response = mvc.perform(delete(QUESTION_ROUTE + "delete/" + primaryKey).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


    @Test
    public void testUpdate() throws Exception {
        MockHttpServletResponse response = mvc.perform(put(QUESTION_ROUTE + "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionDTOJacksonTester.write(questionDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        testIfResponseObjectIsValid(response);
        MockHttpServletResponse nullResponse = mvc.perform(put(QUESTION_ROUTE + "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), nullResponse.getStatus());
    }

    @Test
    public void testAdd() throws Exception {
        MockHttpServletResponse response = mvc.perform(post(QUESTION_ROUTE + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionDTOJacksonTester.write(questionDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        testIfResponseObjectIsValid(response);
        MockHttpServletResponse nullResponse = mvc.perform(post(QUESTION_ROUTE + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), nullResponse.getStatus());
    }

    @Test
    public void testFindBySurvey() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(QUESTION_ROUTE + "getBySurveyId/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(surveyDTOJacksonTester.write(surveyDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonListWriter.parseObject( response.getContentAsString()).get(0).getQuestion(), List.of(questionDTO).get(0).getQuestion());

    }

    private void testIfResponseObjectIsValid(MockHttpServletResponse response) throws IOException {
        String contentAsString = response.getContentAsString();
        QuestionDTOImpl questionDTO = questionDTOJacksonTester.parseObject(contentAsString);
        assertEquals(this.questionDTO.getQuestion(), questionDTO.getQuestion());
    }
    
    
}
