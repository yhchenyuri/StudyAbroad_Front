package com.example.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "success", data);
    }
//    
//    public ApiResponse(boolean success, String message, T data) {
//        this.success = success;
//        this.message = message;
//        this.data = data;
//    }

    // getter setter
}