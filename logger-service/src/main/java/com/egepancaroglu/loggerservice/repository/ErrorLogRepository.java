package com.egepancaroglu.loggerservice.repository;


import com.egepancaroglu.loggerservice.dto.GeneralErrorMessages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author egepancaroglu
 */
@Repository
public interface ErrorLogRepository extends MongoRepository<GeneralErrorMessages, String> {
}
