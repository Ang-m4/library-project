package com.project.library.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException exception) {
        ErrorDTO error = ErrorDTO.builder()
            .code(400).
            message(exception.getMessage())
            .build();
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RequestException exception){
        ErrorDTO error = ErrorDTO.builder()
            .code(exception.getStatus().value())
            .message(exception.getMessage())
            .build();
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(MethodArgumentNotValidException exception){
        ErrorDTO error = ErrorDTO.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(exception.getAllErrors().get(0).getDefaultMessage())
            .build();
        return ResponseEntity.status(error.getCode()).body(error);
    }
}
