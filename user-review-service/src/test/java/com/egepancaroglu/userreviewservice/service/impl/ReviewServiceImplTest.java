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
        // Setup
        List<ReviewDTO> expectedResult = List.of(new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId"));

        // Configure ReviewRepository.findAll(...).
        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        List<Review> reviews = List.of(review);
        when(mockReviewRepository.findAll()).thenReturn(reviews);

        // Configure ReviewMapper.convertToReviewDTO(...).
        ReviewDTO reviewDTO = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");
        when(mockReviewMapper.convertToReviewDTO(any(Review.class))).thenReturn(reviewDTO);

        // Run the test
        List<ReviewDTO> result = reviewServiceImplUnderTest.getAllReviews();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllReviews_ReviewRepositoryReturnsNoItems() {
        // Setup
        when(mockReviewRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        List<ReviewDTO> result = reviewServiceImplUnderTest.getAllReviews();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldGetReviewById() {
        // Setup
        ReviewDTO expectedResult = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");

        // Configure ReviewRepository.findById(...).
        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        // Configure ReviewMapper.convertToReviewDTO(...).
        ReviewDTO reviewDTO = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");
        when(mockReviewMapper.convertToReviewDTO(any(Review.class))).thenReturn(reviewDTO);

        // Run the test
        ReviewDTO result = reviewServiceImplUnderTest.getReviewById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetReviewById_ReviewRepositoryReturnsAbsent() {
        // Setup
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reviewServiceImplUnderTest.getReviewById(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetReviewsByRestaurantId() {
        // Setup
        List<ReviewDTO> expectedResult = List.of(new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId"));

        // Configure ReviewRepository.findReviewsByRestaurantId(...).
        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        List<Review> reviews = List.of(review);
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(reviews);

        // Configure ReviewMapper.convertToReviewDTO(...).
        ReviewDTO reviewDTO = new ReviewDTO(0L, "comment", (byte) 0b0, 0L, "restaurantId");
        when(mockReviewMapper.convertToReviewDTO(any(Review.class))).thenReturn(reviewDTO);

        // Run the test
        List<ReviewDTO> result = reviewServiceImplUnderTest.getReviewsByRestaurantId("restaurantId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetReviewsByRestaurantId_ReviewRepositoryReturnsNoItems() {
        // Setup
        when(mockReviewRepository.findReviewsByRestaurantId("restaurantId")).thenReturn(Collections.emptyList());

        // Run the test
        List<ReviewDTO> result = reviewServiceImplUnderTest.getReviewsByRestaurantId("restaurantId");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldSaveReview() {
        // Setup
        ReviewSaveRequest request = new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId");
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        // Configure ReviewMapper.convertToReview(...).
        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        when(mockReviewMapper.convertToReview(
                new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId"))).thenReturn(review);

        // Configure UserService.getUserEntity(...).
        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        when(mockUserService.getUserEntity(0L)).thenReturn(user1);

        // Configure ReviewRepository.save(...).
        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user2 = new User();
        review1.setUser(user2);
        when(mockReviewRepository.save(any(Review.class))).thenReturn(review1);

        // Configure ReviewRepository.findReviewsByRestaurantId(...).
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

        // Configure ReviewMapper.convertToReviewResponse(...).
        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        // Run the test
        ReviewResponse result = reviewServiceImplUnderTest.saveReview(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldSaveReview_ReviewRepositoryFindReviewsByRestaurantIdReturnsNoItems() {
        // Setup
        ReviewSaveRequest request = new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId");
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        // Configure ReviewMapper.convertToReview(...).
        Review review = new Review();
        review.setId(0L);
        review.setComment("comment");
        review.setRate((short) 0);
        review.setRestaurantId("restaurantId");
        User user = new User();
        review.setUser(user);
        when(mockReviewMapper.convertToReview(
                new ReviewSaveRequest("comment", (byte) 0b0, 0L, "restaurantId"))).thenReturn(review);

        // Configure UserService.getUserEntity(...).
        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        when(mockUserService.getUserEntity(0L)).thenReturn(user1);

        // Configure ReviewRepository.save(...).
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

        // Configure ReviewMapper.convertToReviewResponse(...).
        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        // Run the test
        ReviewResponse result = reviewServiceImplUnderTest.saveReview(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldUpdateReview() {
        // Setup
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, "comment", (byte) 0b0);
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        // Configure ReviewRepository.findById(...).
        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        // Configure ReviewRepository.findReviewsByRestaurantId(...).
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

        // Configure ReviewMapper.convertToReviewResponse(...).
        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        // Run the test
        ReviewResponse result = reviewServiceImplUnderTest.updateReview(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReviewMapper).updateReviewRequestToUser(any(Review.class),
                eq(new ReviewUpdateRequest(0L, "comment", (byte) 0b0)));
        verify(mockReviewRepository).save(any(Review.class));
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldUpdateReview_ReviewRepositoryFindByIdReturnsAbsent() {
        // Setup
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, "comment", (byte) 0b0);
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reviewServiceImplUnderTest.updateReview(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldUpdateReview_ReviewRepositoryFindReviewsByRestaurantIdReturnsNoItems() {
        // Setup
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, "comment", (byte) 0b0);
        ReviewResponse expectedResult = new ReviewResponse("comment", (byte) 0b0);

        // Configure ReviewRepository.findById(...).
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

        // Configure ReviewMapper.convertToReviewResponse(...).
        ReviewResponse reviewResponse = new ReviewResponse("comment", (byte) 0b0);
        when(mockReviewMapper.convertToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        // Run the test
        ReviewResponse result = reviewServiceImplUnderTest.updateReview(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReviewMapper).updateReviewRequestToUser(any(Review.class),
                eq(new ReviewUpdateRequest(0L, "comment", (byte) 0b0)));
        verify(mockReviewRepository).save(any(Review.class));
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldDeleteReviewById() {
        // Setup
        // Configure ReviewRepository.findById(...).
        Review review1 = new Review();
        review1.setId(0L);
        review1.setComment("comment");
        review1.setRate((short) 0);
        review1.setRestaurantId("restaurantId");
        User user = new User();
        review1.setUser(user);
        Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        // Configure ReviewRepository.findReviewsByRestaurantId(...).
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

        // Run the test
        reviewServiceImplUnderTest.deleteReviewById(0L);

        // Verify the results
        verify(mockReviewRepository).deleteById(0L);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }

    @Test
    void shouldDeleteReviewById_ReviewRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reviewServiceImplUnderTest.deleteReviewById(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldDeleteReviewById_ReviewRepositoryFindReviewsByRestaurantIdReturnsNoItems() {
        // Setup
        // Configure ReviewRepository.findById(...).
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

        // Run the test
        reviewServiceImplUnderTest.deleteReviewById(0L);

        // Verify the results
        verify(mockReviewRepository).deleteById(0L);
        verify(mockRestaurantClient).updateRestaurantAverageScore("restaurantId",
                new RestaurantUpdateAverageScoreRequest("restaurantId", 0.0));
    }
}
