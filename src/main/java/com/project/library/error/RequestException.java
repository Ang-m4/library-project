package com.project.library.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RequestException extends RuntimeException {
    
    private HttpStatus status;

    public RequestException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }

}
