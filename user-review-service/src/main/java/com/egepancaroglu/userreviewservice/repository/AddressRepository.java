package com.egepancaroglu.userreviewservice.repository;

import com.egepancaroglu.userreviewservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author egepancaroglu
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
