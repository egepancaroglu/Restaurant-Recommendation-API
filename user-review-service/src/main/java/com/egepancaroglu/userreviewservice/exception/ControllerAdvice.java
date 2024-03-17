package com.egepancaroglu.userreviewservice.exception;

import com.egepancaroglu.userreviewservice.general.GeneralErrorMessages;
import com.egepancaroglu.userreviewservice.general.ValidationErrorMessages;
import com.egepancaroglu.userreviewservice.publisher.ErrorLogProducerService;
import com.egepancaroglu.userreviewservice.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.egepancaroglu.userreviewservice.general.ErrorMessages.METHOD_ARGUMENT_NOT_VALID;


/**
 * @author egepancaroglu
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    private final ErrorLogProducerService errorLogProducerService;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {

        String message = e.getMessage();
        String description = request.getDescription(false);

        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), "Error", "An Error Occured !");

        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages);

        errorLogProducerService.sendMessage(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundExceptions(ItemNotFoundException e, WebRequest request) {

        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);

        var restResponse = RestResponse.error(generalErrorMessages);

        errorLogProducerService.sendMessage(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {

        String description = request.getDescription(false);

        List<FieldError> fieldErrorList = e.getFieldErrors();

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : fieldErrorList) {
            Object rejectedValue = fieldError.getRejectedValue();
            String errorMessage = (rejectedValue != null) ? rejectedValue.toString() : "null";
            errorMap.put(fieldError.getField(), errorMessage + " " + fieldError.getDefaultMessage());
        }

        var validationErrorMessages = new ValidationErrorMessages(LocalDateTime.now(), errorMap, METHOD_ARGUMENT_NOT_VALID.getMessage(), description);
        var restResponse = RestResponse.error(validationErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}