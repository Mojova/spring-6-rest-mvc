package fi.hovukas.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {

        List<Map<String, String>> errorList = exception.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            return errorMap;
        }).toList();

        return ResponseEntity.badRequest().body(errorList) ;
    }

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity handleJPAViolations(TransactionSystemException exception) {
        var responseEntity = ResponseEntity.badRequest();

        if (exception.getCause().getCause() instanceof ConstraintViolationException) {
            var ve = (ConstraintViolationException) exception.getCause().getCause();
            List errors = ve.getConstraintViolations().stream().map(constraintViolation ->  {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                return errorMap;
            }).toList();
            return responseEntity.body(errors);
        }

        return ResponseEntity.badRequest().build();
    }
}
