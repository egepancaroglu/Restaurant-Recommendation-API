package com.egepancaroglu.recommendationservice.service.impl;

import com.egepancaroglu.recommendationservice.client.AddressFeignClient;
import com.egepancaroglu.recommendationservice.dto.AddressDTO;
import com.egepancaroglu.recommendationservice.dto.RestaurantDTO;
import com.egepancaroglu.recommendationservice.exception.ItemNotFoundException;
import com.egepancaroglu.recommendationservice.response.RestResponse;
import com.egepancaroglu.recommendationservice.service.SolrClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    private SolrClientService mockSolrClientService;
    @Mock
    private AddressFeignClient mockAddressFeignClient;

    private RecommendationServiceImpl recommendationServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        recommendationServiceImplUnderTest = new RecommendationServiceImpl(mockSolrClientService,
                mockAddressFeignClient);
    }

    @Test
    void shouldGetRecommendedRestaurantsByUserId() {
        List<RestaurantDTO> expectedResult = List.of(new RestaurantDTO("id", "name", "location", 0.0));

        ResponseEntity<RestResponse<List<AddressDTO>>> restResponseEntity = new ResponseEntity<>(
                new RestResponse<>(List.of(new AddressDTO(0L, "city", "state", "district", "street", "location", 0L)),
                        false), HttpStatusCode.valueOf(200));
        when(mockAddressFeignClient.getAddressesByUserId(0L)).thenReturn(restResponseEntity);

        List<RestaurantDTO> restaurantDTOS = List.of(new RestaurantDTO("id", "name", "location", 0.0));
        when(mockSolrClientService.performSolrQuery("location")).thenReturn(restaurantDTOS);

        List<RestaurantDTO> result = recommendationServiceImplUnderTest.getRecommendedRestaurantsByUserId(0L);

        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    void testGetRecommendedRestaurantsByUserId_AddressFeignClientReturnsNoItems() {
        ResponseEntity<RestResponse<List<AddressDTO>>> addressResponseEntity = ResponseEntity.ok(RestResponse.of(Collections.emptyList()));
        when(mockAddressFeignClient.getAddressesByUserId(0L)).thenReturn(addressResponseEntity);

        assertThrows(ItemNotFoundException.class, () -> {
            recommendationServiceImplUnderTest.getRecommendedRestaurantsByUserId(0L);
        });
    }

}
