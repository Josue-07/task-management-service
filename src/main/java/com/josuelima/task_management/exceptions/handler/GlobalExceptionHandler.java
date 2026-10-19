package com.josuelima.task_management.exceptions.handler;

import com.josuelima.task_management.exceptions.ErrorResponseModel;
import com.josuelima.task_management.exceptions.NotFoundTaskException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

import static com.josuelima.task_management.utils.DateFormatterUtils.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseModel> handleMethodArgumentNotValidException(HttpServletRequest request, BindingResult result) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseModel(dataFormatada(LocalDateTime.now()), request, HttpStatus.BAD_REQUEST, result));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseModel> handleHandlerMethodValidationException(HttpServletRequest request, HandlerMethodValidationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseModel(dataFormatada(LocalDateTime.now()), request, HttpStatus.BAD_REQUEST, e.getParameterValidationResults()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseModel> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseModel(dataFormatada(LocalDateTime.now()), request, HttpStatus.BAD_REQUEST, String.format("Tipo de argumento invalido para o parâmetro {%s}. Valor fornecido: '%s'.", e.getName(), e.getValue())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseModel> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseModel(dataFormatada(LocalDateTime.now()), request, HttpStatus.BAD_REQUEST, "Valor(s) invalido(s) no corpo da requisição. Por favor, verifique os dados fornecidos."));
    }

    @ExceptionHandler(NotFoundTaskException.class)
    public ResponseEntity<ErrorResponseModel> handleNotFoundTaskException(HttpServletRequest request, NotFoundTaskException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseModel(dataFormatada(LocalDateTime.now()), request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }

}