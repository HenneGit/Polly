package com.hahrens.controller.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.repository.AnswerEntityRepository;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.service.AnswerService;
import com.hahrens.controller.implementation.service.AnswerServiceImpl;
import com.hahrens.controller.service.mocks.QuestionServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    private AnswerService answerService;

    @BeforeEach
    public void init(@Mock AnswerEntityRepository entityRepository) {
        QuestionServiceMock questionServiceMock = new QuestionServiceMock();
        answerService = new AnswerServiceImpl(entityRepository, questionServiceMock);
        List<AnswerEntity> answerEntities = new ArrayList<>();
        QuestionEntity questionEntity = QuestionEntity.builder().question("Was?").id(Long.valueOf(222)).build();
        AnswerEntity hallo = AnswerEntity.builder().answerText("Hallo").id(Long.valueOf(2)).questionEntity(questionEntity).build();
        answerEntities.add(hallo);
        lenient().when(entityRepository.findAll()).thenReturn(answerEntities);
    }

    @Test
    public void testFindAll() {
        Collection<AnswerDTO> all = answerService.findAll();
        assertFalse(all.isEmpty());

    }
}
