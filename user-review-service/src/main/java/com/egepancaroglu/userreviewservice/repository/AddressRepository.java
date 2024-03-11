package com.egepancaroglu.userreviewservice.repository;

import com.egepancaroglu.userreviewservice.entity.Address;
import com.egepancaroglu.userreviewservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author egepancaroglu
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

}
