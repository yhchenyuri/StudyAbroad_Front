package com.example.front.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.front.dto.ApiResponse;

@RestControllerAdvice
public class GlobalApi {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handle(RuntimeException e) {

        return new ApiResponse<>(
                false,
                e.getMessage(),
                null
        );
    }    


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException e) {

    	Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(
                error.getField(),           // 欄位名稱
                error.getDefaultMessage()   // 錯誤訊息
            );
        });

        return new ApiResponse<>(
                false,
                "驗證失敗",
                errors
        );
    }
}