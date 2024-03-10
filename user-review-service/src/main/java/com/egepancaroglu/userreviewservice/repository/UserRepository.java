package com.egepancaroglu.userreviewservice.repository;

import com.egepancaroglu.userreviewservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author egepancaroglu
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



}
