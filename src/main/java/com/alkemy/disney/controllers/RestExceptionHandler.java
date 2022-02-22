package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.ApiErrorDTO;
import com.alkemy.disney.exception.ParamNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ParamNotFound.class})
    protected ResponseEntity<Object> handlerParamNotFound(RuntimeException ex, WebRequest request){
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Arrays.asList("Param Not Found")
        );
        return handleExceptionInternal(ex,errorDTO,new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
    }

    //Handler for @Valid annotation

    protected ResponseEntity<Object> handlerMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpStatus status,
            HttpHeaders headers,
            WebRequest request){
        List<String> errors = new ArrayList<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for(ObjectError error : ex.getBindingResult().getGlobalErrors()){
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),errors);
        return handleExceptionInternal(ex,apiError,headers,apiError.getStatus(),request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class,IllegalStateException.class})
    public ResponseEntity<Object> handlerConflict(RuntimeException ex,WebRequest request){
        String bodyOfResponse = "This shoeld be application specific";
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(),HttpStatus.CONFLICT,request);
    }
}
