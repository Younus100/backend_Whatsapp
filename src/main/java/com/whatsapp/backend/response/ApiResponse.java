package com.whatsapp.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.annotation.Aspect;

@Data
@AllArgsConstructor
public class ApiResponse  {
    String message;
}
