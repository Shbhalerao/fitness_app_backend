package com.collaborate.FitnessApp.exceptions;

import com.collaborate.FitnessApp.domain.dto.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String debugMessage, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                debugMessage
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage(), e.toString(), request);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException e, HttpServletRequest request){
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), e.toString(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException e, HttpServletRequest request){
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), e.toString(), request);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServer(InternalServerException e, HttpServletRequest request){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,  e.getMessage(), e.toString(), request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException e, HttpServletRequest request){
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), e.toString(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {
        String debugMessage = e.getMostSpecificCause().getMessage();
        String userMessage = "Invalid data: a database constraint was violated.";

        if (debugMessage != null) {
            if (debugMessage.contains("violates not-null constraint")) {
                Pattern pattern = Pattern.compile("column \"(.*?)\"");
                Matcher matcher = pattern.matcher(debugMessage);
                if (matcher.find()) {
                    String columnName = matcher.group(1);
                    userMessage = "Field '" + columnName + "' cannot be null.";
                }
            } else if (debugMessage.contains("violates unique constraint")) {
                Pattern pattern = Pattern.compile("Detail: Key \\((.*?)\\)=\\((.*?)\\) already exists.");
                Matcher matcher = pattern.matcher(debugMessage);
                if (matcher.find()) {
                    String columnName = matcher.group(1);
                    String value = matcher.group(2);
                    userMessage = "The value '" + value + "' for field '" + columnName + "' already exists.";
                }
            }
        }
        return buildResponse(HttpStatus.BAD_REQUEST, userMessage, debugMessage, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(Exception e, HttpServletRequest request){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.toString(),  request);
    }

}
