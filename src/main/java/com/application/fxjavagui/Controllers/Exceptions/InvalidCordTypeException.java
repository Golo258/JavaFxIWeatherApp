package com.application.fxjavagui.Controllers.Exceptions;

public class InvalidCordTypeException extends AppExceptions {
    public InvalidCordTypeException(String wrongTypeOfArguments) {
        super(wrongTypeOfArguments);
    }
}
