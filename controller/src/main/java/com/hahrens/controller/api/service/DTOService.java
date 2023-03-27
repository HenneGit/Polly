package com.hahrens.controller.api.service;

import java.util.Collection;

/**
 * generic implementation of a service that converts storage objects to dtos.
 * @param <DTO>
 */
public interface DTOService<DTO> {
    /**
     * find all dtos.
     * @return collection with all dtos.
     */
    Collection<DTO> findAll();

    /**
     * find a dto by its id.
     * @param primaryKey the pk to find dto for.
     * @return the found dto.
     */
    DTO findById(Comparable<?> primaryKey);

    /**
     * remove the given dto.
     * @param dto the dto to remove.
     */
    void remove(DTO dto);

    /**
     * create the given dto.
     * @param dto the dto to create.
     * @return the new dto.
     */
    DTO create(DTO dto);

    /**
     * update the given dto.
     * @param dto the dto to update.
     * @return the updated dto.
     */
    DTO update(DTO dto);


}
