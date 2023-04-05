package com.hahrens.webapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.implementation.model.AnswerDTOImpl;
import com.hahrens.controller.implementation.model.QuestionDTOImpl;
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
public class AnswerRestControllerTest {

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerRestController restController;

    private MockMvc mvc;

    private JacksonTester<List<AnswerDTOImpl>> jsonListWriter;
    private JacksonTester<AnswerDTOImpl> answerDTOJsonObjectWriter;
    private JacksonTester<QuestionDTOImpl> questionDTOJsonObjectWriter;

    private UUID primaryKey;
    private UUID questionPk;
    private AnswerDTOImpl answerDTO;
    private AnswerDTOImpl updatedAnswerDTO;
    private QuestionDTOImpl questionDTO;


    @BeforeEach
    public void init() {
        primaryKey = UUID.randomUUID();
        questionPk = UUID.randomUUID();
        JacksonTester.initFields(this, new ObjectMapper());
        answerDTO = new AnswerDTOImpl(primaryKey, questionPk, "New Answer");
        updatedAnswerDTO = new AnswerDTOImpl(primaryKey, questionPk, "New Answer_updated");
        questionDTO = new QuestionDTOImpl(primaryKey, "name", "desc", "question", questionPk);
        lenient().when(answerService.findAll()).thenReturn(List.of(answerDTO));
        lenient().when(answerService.findById(primaryKey.toString())).thenReturn(answerDTO);
        lenient().when(answerService.update(any(AnswerDTOImpl.class))).thenReturn(updatedAnswerDTO);
        lenient().when(answerService.create(any(AnswerDTOImpl.class))).thenReturn(updatedAnswerDTO);
        lenient().when(answerService.findAllByQuestionId(any(Comparable.class))).thenReturn(List.of(updatedAnswerDTO));
        mvc = MockMvcBuilders.standaloneSetup(restController)
                .build();
    }

    @Test
    public void testGetAnswers() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/answer/get").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String contentAsString = response.getContentAsString();
        assertEquals(jsonListWriter.parseObject(contentAsString).get(0).getAnswerText(), List.of(answerDTO).get(0).getAnswerText());
    }

    @Test
    public void testFindById() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/answer/getById/" +primaryKey.toString()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), answerDTOJsonObjectWriter.write(answerDTO).getJson());
        MockHttpServletResponse nullResponse = mvc.perform(get("/answer/getById/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), nullResponse.getStatus());
        MockHttpServletResponse wrongIdResponse = mvc.perform(get("/answer/getById/asdasd").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), wrongIdResponse.getStatus());
    }

    @Test
    public void testDelete() throws Exception {
        MockHttpServletResponse response = mvc.perform(delete("/answer/delete/" + primaryKey.toString()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


    @Test
    public void testUpdate() throws Exception {
        MockHttpServletResponse response = mvc.perform(put("/answer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerDTOJsonObjectWriter.write(updatedAnswerDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        testIfResponseObjectIsValid(response);
        MockHttpServletResponse nullResponse = mvc.perform(put("/answer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), nullResponse.getStatus());
    }

    @Test
    public void testAdd() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/answer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(answerDTOJsonObjectWriter.write(updatedAnswerDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        testIfResponseObjectIsValid(response);
        MockHttpServletResponse nullResponse = mvc.perform(post("/answer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), nullResponse.getStatus());
    }

    @Test
    public void testFindByQuestionId() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/answer/getByQuestionId/" + "someId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionDTOJsonObjectWriter.write(questionDTO).getJson())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonListWriter.parseObject( response.getContentAsString()).get(0).getAnswerText(), List.of(updatedAnswerDTO).get(0).getAnswerText());

    }

    private void testIfResponseObjectIsValid(MockHttpServletResponse response) throws IOException {
        String contentAsString = response.getContentAsString();
        AnswerDTOImpl answerDTO = answerDTOJsonObjectWriter.parseObject(contentAsString);
        assertEquals(updatedAnswerDTO.getAnswerText(), answerDTO.getAnswerText());
    }


}
