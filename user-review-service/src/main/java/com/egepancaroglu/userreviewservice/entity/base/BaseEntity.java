package com.egepancaroglu.userreviewservice.entity.base;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * @author egepancaroglu
 */

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements BaseModel {

    @Embedded
    private BaseAdditionalFields baseAdditionalFields;

}


