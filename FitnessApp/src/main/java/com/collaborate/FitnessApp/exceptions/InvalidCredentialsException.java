package com.collaborate.FitnessApp.exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
