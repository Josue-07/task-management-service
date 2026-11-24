package com.josuelima.task_management.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.validation.method.ParameterValidationResult;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
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
        this(timestamp, request, status);
        this.message = message;

    }

    public ErrorResponseModel(LocalDateTime timestamp, HttpServletRequest request, HttpStatus status, BindingResult result) {
        this(timestamp, request, status);
        this.errors = new LinkedHashMap<>();
        addErrors(result);
    }

    public ErrorResponseModel(LocalDateTime timestamp, HttpServletRequest request, HttpStatus status, List<ParameterValidationResult> results) {
        this(timestamp, request, status);
        addErrors(results);
    }

    private ErrorResponseModel(LocalDateTime timestamp, HttpServletRequest request, HttpStatus status) {
        this.timestamp = timestamp;
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.path = request.getRequestURI();
    }

    private void addErrors(BindingResult result) {
        this.errors = new LinkedHashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    }

    private void addErrors(List<ParameterValidationResult> results) {
        this.errors = new LinkedHashMap<>();
        results.forEach(result -> {
            if (result instanceof ParameterErrors parameterErrors) {
                // objeto com @Valid: chave = nome do campo
                parameterErrors.getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
            } else {
                // valor simples: chave = nome do parâmetro
                String param = result.getMethodParameter().getParameterName();
                result.getResolvableErrors().forEach(error ->
                        errors.merge(param, error.getDefaultMessage(), (a, b) -> a + "; " + b));
            }
        });
    }

}
