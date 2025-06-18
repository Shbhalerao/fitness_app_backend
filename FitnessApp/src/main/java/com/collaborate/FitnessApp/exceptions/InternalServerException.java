package com.collaborate.FitnessApp.exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message){
        super(message);
    }
}
