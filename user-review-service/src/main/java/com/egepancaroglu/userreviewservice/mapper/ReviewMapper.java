package com.egepancaroglu.userreviewservice.mapper;

import com.egepancaroglu.userreviewservice.dto.ReviewDTO;
import com.egepancaroglu.userreviewservice.entity.Review;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


/**
 * @author egepancaroglu
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReviewMapper {

    Review convertToReview(ReviewSaveRequest request);

    @Mapping(target = "user.status", constant = "INACTIVE")
    @Mapping(target = "userId", source = "review.user.id")
    ReviewDTO convertToReviewDTO(Review review);

    void updateReviewRequestToUser(@MappingTarget Review review, ReviewUpdateRequest request);


}
