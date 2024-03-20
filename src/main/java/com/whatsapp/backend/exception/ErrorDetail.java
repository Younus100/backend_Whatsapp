package com.whatsapp.backend.exception;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ErrorDetail {
    private String error;
    private String message;
    private LocalDateTime dateTime;
}
