package com.egepancaroglu.restaurantservice.general;

import java.time.LocalDateTime;

/**
 * @author egepancaroglu
 */

public record GeneralErrorMessages(LocalDateTime date,
                                   String message,
                                   String description) {
}
