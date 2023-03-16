package com.hahrens.controller.implementation.model;

import com.hahrens.controller.api.model.dto.AnswerDTO;

public class AnswerDTOImpl implements AnswerDTO {

    private Comparable<?> primaryKey;

    @Override
    public Comparable<?> getPrimaryKey() {
        return primaryKey;
    }
}
