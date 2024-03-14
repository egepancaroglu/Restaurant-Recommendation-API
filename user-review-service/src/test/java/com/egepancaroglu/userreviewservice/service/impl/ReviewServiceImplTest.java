package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.client.RestaurantClient;
import com.egepancaroglu.userreviewservice.dto.ReviewDTO;
import com.egepancaroglu.userreviewservice.dto.response.ReviewResponse;
import com.egepancaroglu.userreviewservice.entity.Review;
import com.egepancaroglu.userreviewservice.entity.User;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.mapper.ReviewMapper;
import com.egepancaroglu.userreviewservice.repository.ReviewRepository;
import com.egepancaroglu.userreviewservice.request.restaurant.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;
import com.egepancaroglu.userreviewservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository mockReviewRepository;
    @Mock
    private ReviewMapper mockReviewMapper;
    @Mock
    private UserService mockUserService;
    @Mock
    private RestaurantClient mockRestaurantClient;

    private ReviewServiceImpl reviewServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        reviewServiceImplUnderTest = new ReviewServiceImpl(mockReviewRepository, mockReviewMapper, mockUserService,
                mockRestaurantClient);
    }

    @Test
    void shouldGetAllReviews() {
        List<ReviewDTO> expectedResult = List.of(new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId"));

        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        List<Review> reviews = List.of(review);
        when(mockReviewRepository.findAll()).thenReturn(reviews);

        ReviewDTO reviewDTO = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");
        when(mockReviewMapper.convertToReviewDTO(any(Review.class))).thenReturn(reviewDTO);

        List<ReviewDTO> result = reviewServiceImplUnderTest.getAllReviews();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllReviews_ReviewRepositoryReturnsNoItems() {
        when(mockReviewRepository.findAll()).thenReturn(Collections.emptyList());

        List<ReviewDTO> result = reviewServiceImplUnderTest.getAllReviews();

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldGetReviewById() {
        ReviewDTO expectedResult = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");

        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        ReviewDTO reviewDTO = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");
        when(mockReviewMapper.convertToReviewDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewServiceImplUnderTest.getReviewById(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetReviewById_ReviewRepositoryReturnsAbsent() {
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewServiceImplUnderTest.getReviewById(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetReviewsByRestaurantId() {
        List<ReviewDTO> expectedResult = List.of(new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId"));

        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        List<Review> reviews = List.of(review);
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(reviews);

        ReviewDTO reviewDTO = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");
        when(mockReviewMapper.convertToReviewDTO(any(Review.class))).thenReturn(reviewDTO);

        List<ReviewDTO> result = reviewServiceImplUnderTest.getReviewsByRestaurantId("restaurantId");

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetReviewsByRestaurantId_ReviewRepositoryReturnsNoItems() {
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(Collections.emptyList());

        List<ReviewDTO> result = reviewServiceImplUnderTest.getReviewsByRestaurantId("restaurantId");

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldSaveReview() {
        // Setup
        ReviewSaveRequest request = new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId");
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        when(mockReviewMapper.convertToReview(
                new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId"))).thenReturn(review);

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        when(mockUserService.getUserEntity(0L)).thenReturn(user1);

        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user2 = new User();
        review1.setUser(user2);
        when(mockReviewRepository.save(any(Review.class))).thenReturn(review1);

        Review review2 = new Review();
        review2.setId(0L);
        review2.setComment("comment");
        review2.setRate((short) 0);
        review2.setRestaurantId("restaurantId");
        User user3 = new User();
        review2.setUser(user3);
        List<Review> reviews = List.of(review2);
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(reviews);

        when(mockReviewRepository.countReviewByRestaurantId("restaurantId")).thenReturn(0);

        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.saveReview(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldSaveReview_ReviewRepositoryFindReviewsByRestaurantIdReturnsNoItems() {
        // Setup
        ReviewSaveRequest request = new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId");
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        when(mockReviewMapper.convertToReview(
                new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId"))).thenReturn(review);

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        when(mockUserService.getUserEntity(0L)).thenReturn(user1);

        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user2 = new User();
        review1.setUser(user2);
        when(mockReviewRepository.save(any(Review.class))).thenReturn(review1);

        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(Collections.emptyList());
        when(mockReviewRepository.countReviewByRestaurantId("restaurantId")).thenReturn(0);

        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.saveReview(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldUpdateReview() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, "comment", (byte) 0b0);
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        Review review2 = new Review();
        review2.setId(0L);
        review2.setComment("comment");
        review2.setRate((short) 0);
        review2.setRestaurantId("restaurantId");
        User user1 = new User();
        review2.setUser(user1);
        List<Review> reviews = List.of(review2);
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(reviews);

        when(mockReviewRepository.countReviewByRestaurantId("restaurantId")).thenReturn(0);

        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.updateReview(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockReviewMapper).updateReviewRequestToUser(any(Review.class),
                eq(new ReviewUpdateRequest(0L, "comment", (byte) 0b0)));
        verify(mockReviewRepository).save(any(Review.class));
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldUpdateReview_ReviewRepositoryFindByIdReturnsAbsent() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, "comment", (byte) 0b0);
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewServiceImplUnderTest.updateReview(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldUpdateReview_ReviewRepositoryFindReviewsByRestaurantIdReturnsNoItems() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, "comment", (byte) 0b0);
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(Collections.emptyList());
        when(mockReviewRepository.countReviewByRestaurantId("restaurantId")).thenReturn(0);

        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.updateReview(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockReviewMapper).updateReviewRequestToUser(any(Review.class),
                eq(new ReviewUpdateRequest(0L, "comment", (byte) 0b0)));
        verify(mockReviewRepository).save(any(Review.class));
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldDeleteReviewById() {
        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        Review review2 = new Review();
        review2.setId(0L);
        review2.setComment("comment");
        review2.setRate((short) 0);
        review2.setRestaurantId("restaurantId");
        User user1 = new User();
        review2.setUser(user1);
        List<Review> reviews = List.of(review2);
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(reviews);

        when(mockReviewRepository.countReviewByRestaurantId("restaurantId")).thenReturn(0);

        reviewServiceImplUnderTest.deleteReviewById(0L);

        verify(mockReviewRepository).deleteById(0L);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldDeleteReviewById_ReviewRepositoryFindByIdReturnsAbsent() {
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewServiceImplUnderTest.deleteReviewById(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldDeleteReviewById_ReviewRepositoryFindReviewsByRestaurantIdReturnsNoItems() {
        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(Collections.emptyList());
        when(mockReviewRepository.countReviewByRestaurantId("restaurantId")).thenReturn(0);

        reviewServiceImplUnderTest.deleteReviewById(0L);

        verify(mockReviewRepository).deleteById(0L);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }
}
