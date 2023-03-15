package com.hahrens.controller.service;

import com.hahrens.backend.TestEntity;
import com.hahrens.backend.TestEntityRepository;
import com.hahrens.controller.service.api.TestController;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestControllerImpl implements TestController {

    private TestEntityRepository repository;

    public TestControllerImpl(TestEntityRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TestEntityDTO> findAll() {
        return repository.findAll().stream().map(this::toTestEntityDTO).toList();
    }


    private TestEntityDTO toTestEntityDTO(final TestEntity testEntity) {
        return new TestEntityDTO(testEntity.getId(), testEntity.getName(), testEntity.getFirstName());
    }
}
