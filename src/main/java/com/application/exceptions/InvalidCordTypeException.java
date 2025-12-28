package com.application.exceptions;

public class InvalidCordTypeException extends AppExceptions {
    public InvalidCordTypeException(String wrongTypeOfArguments) {
        super(wrongTypeOfArguments);
    }
}
