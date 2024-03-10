package com.egepancaroglu.restaurantservice.repository;

import com.egepancaroglu.restaurantservice.entity.Restaurant;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author egepancaroglu
 */

@Repository
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {


}
