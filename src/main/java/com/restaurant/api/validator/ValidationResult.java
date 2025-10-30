package com.restaurant.api.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data

@AllArgsConstructor
public class ValidationResult {
    
    private boolean valid;
    private Map<String, String> errors;
    
    public ValidationResult() {
        this.valid = true;
        this.errors = new HashMap<>();
    }
    
    public void addError(String field, String message) {
        this.valid = false;
        this.errors.put(field, message);
    }
    
    public boolean hasErrors() {
        return !this.valid;
    }
}
