package com.egepancaroglu.userreviewservice.repository;

import com.egepancaroglu.userreviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author egepancaroglu
 */

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewsByRestaurantId(String restaurantId);

    Integer countReviewByRestaurantId(String restaurantId);

}
