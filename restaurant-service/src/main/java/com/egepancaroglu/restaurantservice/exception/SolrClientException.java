package com.egepancaroglu.restaurantservice.exception;

import com.egepancaroglu.restaurantservice.general.BaseErrorMessage;

/**
 * @author egepancaroglu
 */
public class SolrClientException extends BusinessException {

    public SolrClientException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}
