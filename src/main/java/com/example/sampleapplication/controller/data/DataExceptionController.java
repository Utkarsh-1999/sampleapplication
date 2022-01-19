package com.example.sampleapplication.controller.data;

import com.example.sampleapplication.exception.data.DataAlreadyExistsException;
import com.example.sampleapplication.exception.data.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DataExceptionController {
    @ExceptionHandler(value= DataAlreadyExistsException.class)
    public ResponseEntity<Object> dataAlreadyExistsExceptionHandler(DataAlreadyExistsException exception){
        return new ResponseEntity<>("Data already present", HttpStatus.OK);
    }

    @ExceptionHandler(value= DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFoundExceptionHandler(DataNotFoundException exception){
        return new ResponseEntity<>("Data not found",HttpStatus.OK);
    }
}
