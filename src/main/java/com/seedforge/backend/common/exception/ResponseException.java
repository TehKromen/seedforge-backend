package com.seedforge.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ResponseException {
    private String message;
    private String error;
    private HttpStatus status;
    private Instant time;

}
