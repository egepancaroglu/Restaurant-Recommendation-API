package com.egepancaroglu.loggerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author egepancaroglu
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumedGeneralErrorMessages {

    private LocalDateTime date;
    private String message;
    private String description;

    @Override
    public String toString() {
        return "GeneralErrorMessages{" +
                "date=" + date +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
