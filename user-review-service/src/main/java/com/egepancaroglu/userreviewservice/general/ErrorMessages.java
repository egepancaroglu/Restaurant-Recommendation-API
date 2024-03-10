package com.egepancaroglu.userreviewservice.general;

import lombok.Getter;

/**
 * @author egepancaroglu
 */

@Getter
public enum ErrorMessages implements BaseErrorMessage {

    USER_NOT_FOUND("User Not Found !"),
    REVIEW_NOT_FOUND("Review Not Found !"),
    ADDRESS_NOT_FOUND("Address Not Found !"),
    METHOD_ARGUMENT_NOT_VALID("Method Argument Not Valid !");
    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
