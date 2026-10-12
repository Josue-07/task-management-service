package com.josuelima.task_management.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@JsonPropertyOrder({"timestamp", "method", "status", "statusText", "path", "message", "errors"})
public class ErrorResponseModel {

    private final LocalDateTime timestamp;
    private final String method;
    private final int status;
    private final String statusText;
    private final String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorResponseModel(LocalDateTime timestamp, HttpServletRequest request, HttpStatus status, String message) {
        this.timestamp = timestamp;
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.path = request.getRequestURI();
        this.message = message;

    }

    public ErrorResponseModel(LocalDateTime timestamp, HttpServletRequest request, HttpStatus status, BindingResult result) {
        this.timestamp = timestamp;
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.path = request.getRequestURI();
        addErrors(result);
    }

    private void addErrors(BindingResult result){
        this.errors = new LinkedHashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
     }

}
