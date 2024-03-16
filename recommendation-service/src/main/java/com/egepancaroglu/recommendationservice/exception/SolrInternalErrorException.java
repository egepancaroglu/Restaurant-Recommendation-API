package com.egepancaroglu.recommendationservice.exception;

import com.egepancaroglu.recommendationservice.general.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author egepancaroglu
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class SolrInternalErrorException extends BusinessException {

    public SolrInternalErrorException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }

}

