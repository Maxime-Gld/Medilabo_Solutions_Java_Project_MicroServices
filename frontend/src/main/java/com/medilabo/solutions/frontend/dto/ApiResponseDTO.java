package com.medilabo.solutions.frontend.dto;
import java.util.List;

import lombok.Data;

@Data
public class ApiResponseDTO<T> {
    
    private T data;
    private List<String> errors;

    public ApiResponseDTO(T data) {
        this.data = data;
        this.errors = null;
    }

    public ApiResponseDTO(List<String> errors) {
        this.errors = errors;
        this.data = null;
    }
}
