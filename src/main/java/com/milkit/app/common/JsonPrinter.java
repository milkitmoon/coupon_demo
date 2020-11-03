package com.milkit.app.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JsonPrinter implements PrintInterface {

    @Override
    public void print(Object printObj) throws Exception {
        log.debug( (new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(printObj) );
    }
    
}