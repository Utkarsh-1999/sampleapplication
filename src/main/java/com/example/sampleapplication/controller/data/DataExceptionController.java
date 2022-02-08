package com.example.sampleapplication.controller.data;

import com.example.sampleapplication.exception.data.DataAlreadyExistsException;
import com.example.sampleapplication.exception.data.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DataExceptionController {

    Logger logger= LoggerFactory.getLogger(DataExceptionController.class);
    @ExceptionHandler(value= DataAlreadyExistsException.class)
    public ResponseEntity<Object> dataAlreadyExistsExceptionHandler(DataAlreadyExistsException exception){
        logger.info(exception.toString());
        return new ResponseEntity<>("Data already present", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value= DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFoundExceptionHandler(DataNotFoundException exception){
        logger.info(exception.toString());
        return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value= Exception.class)
    public ResponseEntity<Object> universalErrorHandler(Exception exception){
        logger.info(exception.toString());
        return new ResponseEntity<>("Error encountered",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
