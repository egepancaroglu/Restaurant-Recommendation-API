package com.egepancaroglu.restaurantservice.general;

import lombok.Getter;

/**
 * @author egepancaroglu
 */
@Getter
public enum ErrorMessages implements BaseErrorMessage {

    RESTAURANT_NOT_FOUND("Restaurant Not Found !"),
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
