package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.dto.SurveyDTO;

import java.util.UUID;

public class SurveyDTOImpl implements SurveyDTO {

    private UUID primaryKey;
    private String name;
    private String description;

    public SurveyDTOImpl(UUID primaryKey, String name, String description) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
    }

    @Override
    public UUID getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
