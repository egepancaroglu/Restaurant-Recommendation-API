package com.egepancaroglu.recommendationservice.exception;


import com.egepancaroglu.recommendationservice.general.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author egepancaroglu
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SolrClientException extends BusinessException {

    public SolrClientException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}
