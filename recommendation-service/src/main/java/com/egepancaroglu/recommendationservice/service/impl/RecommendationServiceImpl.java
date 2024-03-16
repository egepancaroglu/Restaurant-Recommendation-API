package com.egepancaroglu.recommendationservice.service.impl;

import com.egepancaroglu.recommendationservice.client.AddressFeignClient;
import com.egepancaroglu.recommendationservice.dto.AddressDTO;
import com.egepancaroglu.recommendationservice.dto.RestaurantDTO;
import com.egepancaroglu.recommendationservice.exception.ItemNotFoundException;
import com.egepancaroglu.recommendationservice.general.ErrorMessages;
import com.egepancaroglu.recommendationservice.response.RestResponse;
import com.egepancaroglu.recommendationservice.service.RecommendationService;
import com.egepancaroglu.recommendationservice.service.SolrClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author egepancaroglu
 */

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final SolrClientService solrClientService;
    private final AddressFeignClient addressFeignClient;

    @Override
    public List<RestaurantDTO> getRecommendedRestaurantsByUserId(Long userId) {

        ResponseEntity<RestResponse<List<AddressDTO>>> addressDTOResponse = addressFeignClient.getAddressesByUserId(userId);

        RestResponse<List<AddressDTO>> responseBody = addressDTOResponse.getBody();
        if (responseBody == null || responseBody.getData() == null || responseBody.getData().isEmpty()) {
            throw new ItemNotFoundException(ErrorMessages.USER_ADDRESS_NOT_FOUND);
        }

        List<AddressDTO> addressDTOList = responseBody.getData();

        AddressDTO firstAddress = addressDTOList.get(0);

        String userLocation = firstAddress.location().replaceAll(",\\s+", ",");

        return solrClientService.performSolrQuery(userLocation);

    }

}
