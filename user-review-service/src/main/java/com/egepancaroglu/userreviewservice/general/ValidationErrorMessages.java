package com.egepancaroglu.userreviewservice.general;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author egepancaroglu
 */

public record ValidationErrorMessages(LocalDateTime date,
                                      Map<String, String> errors,
                                      String message,
                                      String description) {
}