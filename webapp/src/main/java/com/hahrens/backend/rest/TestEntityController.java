package com.hahrens.backend.rest;

import com.hahrens.controller.service.api.TestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TestEntityController {

    private final TestController controller;

    public TestEntityController(TestController testEntityRepository) {
        this.controller = testEntityRepository;
    }

    @GetMapping("/testEntity")
    public ResponseEntity<Collection<TestEntityDTO>> getAllTestEntity() {
        return ResponseEntity.ok(controller.findAll());
    }

}

