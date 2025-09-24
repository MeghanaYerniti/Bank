//package com.example.Bank.exception;
//
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(InsufficientFundsException.class)
//    public ResponseEntity<Map<String, Object>> handleInsufficientFundsException(InsufficientFundsException ex) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Insufficient Funds", ex.getMessage());
//    }
//
//    @ExceptionHandler(MinimumBalanceException.class)
//    public ResponseEntity<Map<String, Object>> handleMinimumBalanceException(MinimumBalanceException ex) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Minimum Balance Violation", ex.getMessage());
//    }
//
//    @ExceptionHandler(HighValueTransactionException.class)
//    public ResponseEntity<Map<String, Object>> handleHighValueTransactionException(HighValueTransactionException ex) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "High Value Transaction", ex.getMessage());
//    }
//
//    @ExceptionHandler(RequiresPanException.class)
//    public ResponseEntity<Map<String, Object>> handleRequiresPanException(RequiresPanException ex) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "PAN Required", ex.getMessage());
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        StringBuilder message = new StringBuilder("Validation failed: ");
//        ex.getBindingResult().getAllErrors().forEach(error -> message.append(error.getDefaultMessage()).append("; "));
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message.toString());
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
//        StringBuilder message = new StringBuilder("Validation failed: ");
//        ex.getConstraintViolations().forEach(violation -> message.append(violation.getMessage()).append("; "));
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message.toString());
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
//        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
//    }
//
//    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now().toString());
//        response.put("status", status.value());
//        response.put("error", error);
//        response.put("message", message);
//        response.put("path", "");
//        return new ResponseEntity<>(response, status);
//    }
//}







package com.example.Bank.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Insufficient Funds", ex.getMessage(), request);
    }

    @ExceptionHandler(MinimumBalanceException.class)
    public ResponseEntity<Map<String, Object>> handleMinimumBalanceException(MinimumBalanceException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Minimum Balance Violation", ex.getMessage(), request);
    }

    @ExceptionHandler(HighValueTransactionException.class)
    public ResponseEntity<Map<String, Object>> handleHighValueTransactionException(HighValueTransactionException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "High Value Transaction", ex.getMessage(), request);
    }

    @ExceptionHandler(RequiresPanException.class)
    public ResponseEntity<Map<String, Object>> handleRequiresPanException(RequiresPanException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "PAN Required", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getAllErrors().forEach(error -> message.append(error.getDefaultMessage()).append("; "));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message.toString(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        ex.getConstraintViolations().forEach(violation -> message.append(violation.getMessage()).append("; "));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message.toString(), request);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Map<String, Object>> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        Throwable cause = ex.getMostSpecificCause();
        String message = cause != null ? cause.getMessage() : "Could not commit JPA transaction";
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction Error", message, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", ex.getMessage(), request);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        response.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(response, status);
    }
}