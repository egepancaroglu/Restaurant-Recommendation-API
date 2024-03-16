package com.egepancaroglu.recommendationservice.exception;


import com.egepancaroglu.recommendationservice.general.GeneralErrorMessages;
import com.egepancaroglu.recommendationservice.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


/**
 * @author egepancaroglu
 */

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {

        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), "Error", "Bir hata oluştu");

        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotFoundExceptions(ItemNotFoundException e, WebRequest request) {

        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);

        var restResponse = RestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleSolrClientExceptions(SolrClientException e, WebRequest request) {

        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);

        var restResponse = RestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleSolrInternalErrorExceptions(SolrInternalErrorException e, WebRequest request) {

        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);

        var restResponse = RestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }


}
