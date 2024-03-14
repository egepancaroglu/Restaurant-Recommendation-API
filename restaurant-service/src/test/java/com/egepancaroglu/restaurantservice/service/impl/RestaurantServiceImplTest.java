package com.egepancaroglu.restaurantservice.service.impl;

import com.egepancaroglu.restaurantservice.client.AddressFeignClient;
import com.egepancaroglu.restaurantservice.client.SolrClientService;
import com.egepancaroglu.restaurantservice.dto.AddressDTO;
import com.egepancaroglu.restaurantservice.dto.RestaurantDTO;
import com.egepancaroglu.restaurantservice.entity.Restaurant;
import com.egepancaroglu.restaurantservice.enums.Status;
import com.egepancaroglu.restaurantservice.exception.ItemNotFoundException;
import com.egepancaroglu.restaurantservice.mapper.RestaurantMapper;
import com.egepancaroglu.restaurantservice.repository.RestaurantRepository;
import com.egepancaroglu.restaurantservice.request.RestaurantSaveRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateRequest;
import com.egepancaroglu.restaurantservice.response.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository mockRestaurantRepository;
    @Mock
    private RestaurantMapper mockRestaurantMapper;
    @Mock
    private SolrClientService mockSolrClientService;
    @Mock
    private AddressFeignClient mockAddressFeignClient;

    private RestaurantServiceImpl restaurantServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        restaurantServiceImplUnderTest = new RestaurantServiceImpl(mockRestaurantRepository, mockRestaurantMapper,
                mockSolrClientService, mockAddressFeignClient);
    }

    @Test
    void testGetRestaurantById() {
        // Setup
        final RestaurantDTO expectedResult = new RestaurantDTO("id", "name", "location", 0.0);

        // Configure RestaurantRepository.findById(...).
        final Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("id", "name", "location", 0.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        // Configure RestaurantMapper.convertRestaurantToRestaurantDTO(...).
        final RestaurantDTO restaurantDTO = new RestaurantDTO("id", "name", "location", 0.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        // Run the test
        final RestaurantDTO result = restaurantServiceImplUnderTest.getRestaurantById("id");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetRestaurantById_RestaurantRepositoryReturnsAbsent() {
        // Setup
        when(mockRestaurantRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> restaurantServiceImplUnderTest.getRestaurantById("id"))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testGetAllRestaurants() {
        // Setup
        final List<RestaurantDTO> expectedResult = List.of(new RestaurantDTO("id", "name", "location", 0.0));

        // Configure RestaurantRepository.findAll(...).
        final Iterable<Restaurant> restaurants = List.of(new Restaurant("id", "name", "location", 0.0, Status.ACTIVE));
        when(mockRestaurantRepository.findAll()).thenReturn(restaurants);

        // Configure RestaurantMapper.convertRestaurantToRestaurantDTO(...).
        final RestaurantDTO restaurantDTO = new RestaurantDTO("id", "name", "location", 0.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        // Run the test
        final List<RestaurantDTO> result = restaurantServiceImplUnderTest.getAllRestaurants();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllRestaurants_RestaurantRepositoryReturnsNoItems() {
        // Setup
        when(mockRestaurantRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<RestaurantDTO> result = restaurantServiceImplUnderTest.getAllRestaurants();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetRecommendedRestaurantsByUserId() {
        // Setup
        final List<RestaurantDTO> expectedResult = List.of(new RestaurantDTO("0L", "name", "45.54554,45.454543", 0.0));

        // Configure AddressFeignClient.getAddressesByUserId(...).
        final ResponseEntity<RestResponse<List<AddressDTO>>> restResponseEntity = new ResponseEntity<>(
                new RestResponse<>(List.of(new AddressDTO(0L, "city", "state", "district", "street", "45.54554,45.454543", 0L)),
                        false), HttpStatus.OK);
        when(mockAddressFeignClient.getAddressesByUserId(0L)).thenReturn(restResponseEntity);

        // Configure SolrClientService.performSolrQuery(...).
        final List<Restaurant> restaurantList = List.of(new Restaurant("0L", "name", "45.54554,45.454543", 0.0, Status.ACTIVE));
        when(mockSolrClientService.performSolrQuery("45.54554,45.454543")).thenReturn(restaurantList);

        // Configure RestaurantMapper.convertRestaurantsToRestaurantDTOs(...).
        final List<RestaurantDTO> restaurantDTOList = List.of(new RestaurantDTO("0L", "name", "45.54554,45.454543", 0.0));
        when(mockRestaurantMapper.convertRestaurantsToRestaurantDTOs(anyList()))
                .thenReturn(restaurantDTOList);

        // Run the test
        final List<RestaurantDTO> result = restaurantServiceImplUnderTest.getRecommendedRestaurantsByUserId(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void testSaveRestaurant() {
        // Setup
        final RestaurantSaveRequest request = new RestaurantSaveRequest("name", "location");
        final RestaurantDTO expectedResult = new RestaurantDTO("id", "name", "location", 0.0);

        // Configure RestaurantMapper.convertSaveRequestToRestaurant(...).
        final Restaurant restaurant = new Restaurant("id", "name", "location", 0.0, Status.ACTIVE);
        when(mockRestaurantMapper.convertSaveRequestToRestaurant(
                new RestaurantSaveRequest("name", "location"))).thenReturn(restaurant);

        // Configure RestaurantRepository.save(...).
        final Restaurant restaurant1 = new Restaurant("id", "name", "location", 0.0, Status.ACTIVE);
        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        // Configure RestaurantMapper.convertRestaurantToRestaurantDTO(...).
        final RestaurantDTO restaurantDTO = new RestaurantDTO("id", "name", "location", 0.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        // Run the test
        final RestaurantDTO result = restaurantServiceImplUnderTest.saveRestaurant(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateRestaurant() {
        // Setup
        final RestaurantUpdateRequest request = new RestaurantUpdateRequest("id", "name", "longitude");
        final RestaurantDTO expectedResult = new RestaurantDTO("id", "name", "location", 0.0);

        // Configure RestaurantRepository.findById(...).
        final Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("id", "name", "location", 0.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        // Configure RestaurantMapper.convertRestaurantToRestaurantDTO(...).
        final RestaurantDTO restaurantDTO = new RestaurantDTO("id", "name", "location", 0.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        // Run the test
        final RestaurantDTO result = restaurantServiceImplUnderTest.updateRestaurant(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantMapper).updateRestaurantRequestToRestaurant(any(Restaurant.class),
                eq(new RestaurantUpdateRequest("id", "name", "longitude")));
        verify(mockRestaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant_RestaurantRepositoryFindByIdReturnsAbsent() {
        // Setup
        final RestaurantUpdateRequest request = new RestaurantUpdateRequest("id", "name", "longitude");
        when(mockRestaurantRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> restaurantServiceImplUnderTest.updateRestaurant(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testUpdateRestaurantAverageScore() {
        // Setup
        final RestaurantUpdateAverageScoreRequest request = new RestaurantUpdateAverageScoreRequest("id", 0.0);
        final RestaurantDTO expectedResult = new RestaurantDTO("id", "name", "location", 0.0);

        // Configure RestaurantRepository.findById(...).
        final Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("id", "name", "location", 0.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        // Configure RestaurantMapper.convertRestaurantToRestaurantDTO(...).
        final RestaurantDTO restaurantDTO = new RestaurantDTO("id", "name", "location", 0.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        // Run the test
        final RestaurantDTO result = restaurantServiceImplUnderTest.updateRestaurantAverageScore(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRestaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurantAverageScore_RestaurantRepositoryFindByIdReturnsAbsent() {
        // Setup
        final RestaurantUpdateAverageScoreRequest request = new RestaurantUpdateAverageScoreRequest("id", 0.0);
        when(mockRestaurantRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> restaurantServiceImplUnderTest.updateRestaurantAverageScore(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testDeleteRestaurant() {
        // Setup
        // Run the test
        restaurantServiceImplUnderTest.deleteRestaurant("id");

        // Verify the results
        verify(mockRestaurantRepository).deleteById("id");
    }

    @Test
    void testActivateRestaurant() {
        // Setup
        final RestaurantDTO expectedResult = new RestaurantDTO("id", "name", "location", 0.0);

        // Configure RestaurantRepository.findById(...).
        final Optional<Restaurant> restaurant = Optional.of(
                new Restaurant("id", "name", "location", 0.0, Status.ACTIVE));
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        // Configure RestaurantRepository.save(...).
        final Restaurant restaurant1 = new Restaurant("id", "name", "location", 0.0, Status.ACTIVE);
        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        // Configure RestaurantMapper.convertRestaurantToRestaurantDTO(...).
        final RestaurantDTO restaurantDTO = new RestaurantDTO("id", "name", "location", 0.0);
        when(mockRestaurantMapper.convertRestaurantToRestaurantDTO(any(Restaurant.class))).thenReturn(restaurantDTO);

        // Run the test
        final RestaurantDTO result = restaurantServiceImplUnderTest.activateRestaurant("id");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testActivateRestaurant_RestaurantRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRestaurantRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> restaurantServiceImplUnderTest.activateRestaurant("id"))
                .isInstanceOf(ItemNotFoundException.class);
    }
}
