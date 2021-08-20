package com.tushar.securitycourse.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ApiError> handleSQLSyntaxErrorException(Exception exception) {
        log.info("Inside AppException ---------------> handleSQLSyntaxErrorException");
        ApiError apiError = new ApiError();
        apiError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setErrorMessage(exception.getMessage());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        log.info("Inside AppException ---------------> handleException");
        ApiError apiError = new ApiError();
        apiError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setErrorMessage(exception.getMessage());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(Exception exception) {
        log.info("Inside AppException ---------------> handleUsernameNotFoundException");
        ApiError apiError = new ApiError();
        apiError.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        apiError.setErrorMessage(exception.getMessage());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED);
    }

}
