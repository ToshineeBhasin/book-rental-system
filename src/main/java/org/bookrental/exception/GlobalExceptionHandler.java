package org.bookrental.exception;

import org.bookrental.dto.common.ApiResponse;

import org.bookrental.common.enums.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice → this class handles exceptions globally
//@ExceptionHandler → this method handles this specific exception
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage() )
        );

        return new ApiResponse<>(
               ResponseStatus.FAILED, "Validation Failed", errors
                );
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBookAlreadyExistsException(BookAlreadyExistsException exception){
        ApiResponse<Object> response = new ApiResponse<>(
                ResponseStatus.FAILED,
                exception.getMessage(),
                null
        );
        return new ResponseEntity<>( response, HttpStatus.CONFLICT );

    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleBookNotFoundException(BookNotFoundException exception){
        ApiResponse<Object> response = new ApiResponse<>(
                ResponseStatus.FAILED,
                exception.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ApiResponse<Object> response = new ApiResponse<>(
             ResponseStatus.FAILED,
             exception.getMessage(),
             null
        );

        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}
//From the exception,
//get the validation report,
//get all field errors,
//for every field error,
//store field name and its message into the map.
