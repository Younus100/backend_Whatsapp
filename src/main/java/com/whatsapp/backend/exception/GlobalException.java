package com.whatsapp.backend.exception;

import com.whatsapp.backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> UserExceprionHandler(UserException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessage(exception.getMessage());
        errorDetail.setDateTime(LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> ExceprionHandler(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessage(exception.getMessage());
        errorDetail.setDateTime(LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
