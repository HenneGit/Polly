package com.hahrens.webapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(MockitoExtension.class)
public class AnswerRestControllerTest {

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerRestController restController;

    private MockMvc mvc;

    private JacksonTester<List<AnswerDTOImpl>> jsonListWriter;
    private JacksonTester<AnswerDTOImpl> jsonObjectWriter;

    private UUID primaryKey;
    private UUID questionPk;


    @BeforeEach
    public void init() {
        primaryKey = UUID.randomUUID();
        questionPk = UUID.randomUUID();
        JacksonTester.initFields(this, new ObjectMapper());
        AnswerDTO answerDTO = new AnswerDTOImpl(primaryKey, questionPk, "New Answer");
        lenient().when(answerService.findAll()).thenReturn(List.of(answerDTO));
        lenient().when(answerService.findById(primaryKey.toString())).thenReturn(answerDTO);
        mvc = MockMvcBuilders.standaloneSetup(restController)
                .build();
    }

    @Test
    public void testGetAnswers() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/answer/get").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jsonListWriter.write(List.of(new AnswerDTOImpl(primaryKey, questionPk, "New Answer"))).getJson());
    }

    @Test
    public void testFindById() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/answer/" +primaryKey.toString()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jsonObjectWriter.write(new AnswerDTOImpl(primaryKey, questionPk, "New Answer")).getJson());
        MockHttpServletResponse nullResponse = mvc.perform(get("/answer/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(nullResponse.getStatus(), HttpStatus.NOT_FOUND.value());
        MockHttpServletResponse wrongIdResponse = mvc.perform(get("/answer/asdasd").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(wrongIdResponse.getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDelete() {



    }



}
