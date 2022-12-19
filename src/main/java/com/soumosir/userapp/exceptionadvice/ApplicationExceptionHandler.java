package com.soumosir.userapp.exceptionadvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.soumosir.userapp.exception.UserExistException;
import com.soumosir.userapp.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Map> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> fieldMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldMap.put(error.getField(), error.getDefaultMessage());
        });
        Map<String, Map> errorMap = new HashMap<>();
        errorMap.put("errorMessage",fieldMap);
        log.error(errorMap.toString());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        log.error(ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistException.class)
    public Map<String, String> handleUserExistException(UserExistException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        log.error(ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public Map<String, Map> handleDateTimeParseException(DateTimeParseException ex) {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("dateParseError", ex.getMessage()+". Expected format MM/dd/yyyy.");
        Map<String, Map> errorMap = new HashMap<>();
        errorMap.put("errorMessage", fieldMap);
        log.error(ex.getMessage());
        return errorMap;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(Exception.class)
//    public Map<String, String> handleException(Exception ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put("errorMessage", ex.getMessage());
//        log.error(ex.getMessage());
//        return errorMap;
//    }

}
