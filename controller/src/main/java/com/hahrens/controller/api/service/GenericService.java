package com.hahrens.controller.api.service;

public interface GenericService<DTO> {


    DTO findById(Comparable<?> pk);



}
