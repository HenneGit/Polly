package com.hahrens.controller.api.service;

import java.util.Collection;

public interface DTOService<DTO> {

    Collection<DTO> findAll();

    DTO findById(Comparable<?> primaryKey);

    void remove(DTO dto);

    DTO create(DTO dto);

    DTO update(DTO dto);


}
