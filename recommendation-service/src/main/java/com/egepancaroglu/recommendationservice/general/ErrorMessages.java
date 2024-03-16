package com.egepancaroglu.recommendationservice.general;

import lombok.Getter;

/**
 * @author egepancaroglu
 */
@Getter
public enum ErrorMessages implements BaseErrorMessage {

    USER_ADDRESS_NOT_FOUND("User Have Not Address !"),
    SOLR_SERVER_ERROR("Internal Error At Solr Server !"),
    CANNOT_FETCH_DATA("Cannot fetch data from Solr Client !");
    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
