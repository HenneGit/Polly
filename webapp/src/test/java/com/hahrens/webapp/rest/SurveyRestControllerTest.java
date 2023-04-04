package com.hahrens.webapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahrens.controller.api.service.SurveyService;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
import com.hahrens.controller.implementation.model.SurveyDTOImpl;
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
public class SurveyRestControllerTest {


    @Mock
    private SurveyService questionService;

    @InjectMocks
    private SurveyRestController restController;

    private static final String SURVEY_ROUTE = "/survey/";
    private MockMvc mvc;
    private UUID primaryKey;

    private JacksonTester<List<SurveyDTOImpl>> jsonListWriter;
    private JacksonTester<SurveyDTOImpl> questionDTOJacksonTester;
    private SurveyDTOImpl surveyDTO;

    @BeforeEach
    public void init() {
        primaryKey = UUID.randomUUID();
        JacksonTester.initFields(this, new ObjectMapper());
        surveyDTO = new SurveyDTOImpl(UUID.randomUUID(), "Survey", "Desc");
        lenient().when(questionService.findAll()).thenReturn(List.of(surveyDTO));
        lenient().when(questionService.findById(primaryKey.toString())).thenReturn(surveyDTO);
        lenient().when(questionService.update(any(SurveyDTOImpl.class))).thenReturn(surveyDTO);
        lenient().when(questionService.create(any(SurveyDTOImpl.class))).thenReturn(surveyDTO);
        mvc = MockMvcBuilders.standaloneSetup(restController)
                .build();
    }
    @Test
    public void testGetQuestions() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(SURVEY_ROUTE + "get").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String contentAsString = response.getContentAsString();
        assertEquals(jsonListWriter.parseObject(contentAsString).get(0).getName(), List.of(surveyDTO).get(0).getName());
    }

    @Test
    public void testFindById() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(SURVEY_ROUTE +primaryKey.toString()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), questionDTOJacksonTester.write(surveyDTO).getJson());
        MockHttpServletResponse nullResponse = mvc.perform(get(SURVEY_ROUTE).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), nullResponse.getStatus());
        MockHttpServletResponse wrongIdResponse = mvc.perform(get(SURVEY_ROUTE + "asdasd").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), wrongIdResponse.getStatus());
    }

    @Test
    public void testDelete() throws Exception {
        MockHttpServletResponse response = mvc.perform(delete(SURVEY_ROUTE + "delete/" + primaryKey.toString()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


    @Test
    public void testUpdate() throws Exception {
        MockHttpServletResponse response = mvc.perform(put(SURVEY_ROUTE + "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionDTOJacksonTester.write(surveyDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        testIfResponseObjectIsValid(response);
        MockHttpServletResponse nullResponse = mvc.perform(put(SURVEY_ROUTE + "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), nullResponse.getStatus());
    }

    @Test
    public void testAdd() throws Exception {
        MockHttpServletResponse response = mvc.perform(post(SURVEY_ROUTE + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionDTOJacksonTester.write(surveyDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        testIfResponseObjectIsValid(response);
        MockHttpServletResponse nullResponse = mvc.perform(post(SURVEY_ROUTE + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), nullResponse.getStatus());
    }

    private void testIfResponseObjectIsValid(MockHttpServletResponse response) throws IOException {
        String contentAsString = response.getContentAsString();
        SurveyDTOImpl questionDTO = questionDTOJacksonTester.parseObject(contentAsString);
        assertEquals(this.surveyDTO.getName(), questionDTO.getName());
    }

}
