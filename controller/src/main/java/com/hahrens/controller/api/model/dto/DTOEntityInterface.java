package com.hahrens.controller.api.model.dto;

public interface DTOEntityInterface {

    Comparable<?> getPrimaryKey();

    boolean equals(DTOEntityInterface dtoEntityInterface);

}
