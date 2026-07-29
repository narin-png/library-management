package dev.joint.library_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>> handleValidation(

            MethodArgumentNotValidException ex) {



        Map<String, String> errors = new HashMap<>();



        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            errors.put(error.getField(), error.getDefaultMessage());

        }



        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(Exception.class)

    public ResponseEntity<String> handleException(Exception ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @ExceptionHandler(ResourceNotFoundException.class)

    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(UsernameAlreadyExistsException.class)

    public ResponseEntity<String> handleUsernameAlreadyExists(

            UsernameAlreadyExistsException ex) {



        return new ResponseEntity<>(

                ex.getMessage(),

                HttpStatus.CONFLICT

        );

    }



    @ExceptionHandler(BadCredentialsException.class)

    public ResponseEntity<String> handleBadCredentials(

            BadCredentialsException ex) {



        return new ResponseEntity<>(

                ex.getMessage(),

                HttpStatus.UNAUTHORIZED

        );

    }
}
