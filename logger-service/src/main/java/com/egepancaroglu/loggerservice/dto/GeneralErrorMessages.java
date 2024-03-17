package com.egepancaroglu.loggerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author egepancaroglu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("error_logs")
public class GeneralErrorMessages {

    @Id
    private ObjectId id;
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
