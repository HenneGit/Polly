package com.hahrens.backend.repository;

import com.hahrens.backend.model.SurveyEntity;
import graphql.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
public class SurveyEntityRepositoryTest {

    @Autowired
    private SurveyEntityRepository entityRepository;

    @BeforeEach
    public void setup() {
        SurveyEntity surveyEntity = new SurveyEntity(null,"Hallo", "Desc", null);
        entityRepository.save(surveyEntity);
    }
    @Test
    public void testFindAll() {
        List<SurveyEntity> all = entityRepository.findAll();
        Assert.assertTrue(all.get(0).getName().equals("Hallo"));

    }
}
