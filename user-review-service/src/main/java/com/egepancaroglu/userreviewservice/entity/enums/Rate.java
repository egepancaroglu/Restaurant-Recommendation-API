package com.egepancaroglu.userreviewservice.entity.enums;

import lombok.Getter;

/**
 * @author egepancaroglu
 */

@Getter
public enum Rate {

    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final int value;

    Rate(int value) {
        this.value = value;
    }

}

