package com.egepancaroglu.recommendationservice.service;

import com.egepancaroglu.recommendationservice.dto.RestaurantDTO;
import com.egepancaroglu.recommendationservice.exception.SolrClientException;
import com.egepancaroglu.recommendationservice.exception.SolrInternalErrorException;
import com.egepancaroglu.recommendationservice.general.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author egepancaroglu
 */

@Service
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SolrClientService {

    @Value("${solr.client.url}")
    private String solrUrl;

    public List<RestaurantDTO> performSolrQuery(String userLocation) {
        try (HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build()) {

            final Map<String, String> queryParamMap = new HashMap<>();

            queryParamMap.put("q", "*:*");
            queryParamMap.put("fq", "fq={!geofilt pt=" + userLocation + " sfield=location d=10}");
            queryParamMap.put("start", "0");
            queryParamMap.put("rows", "3");
            queryParamMap.put("sort", "sum(mul(averageScore,14),mul(sub(10,geodist(" + userLocation + ",location)),3)) desc");

            MapSolrParams queryParams = new MapSolrParams(queryParamMap);
            QueryResponse queryResponse = solrClient.query(queryParams);
            SolrDocumentList results = queryResponse.getResults();

            List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
            for (SolrDocument solrDocument : results) {
                String restaurantId = solrDocument.get("id").toString();
                String restaurantName = solrDocument.get("name").toString();
                String restaurantLocation = solrDocument.get("location").toString();
                Double restaurantAverageScore = (Double) solrDocument.get("averageScore");
                RestaurantDTO restaurantDTO = new RestaurantDTO(restaurantId, restaurantName, restaurantLocation, restaurantAverageScore);
                restaurantDTOList.add(restaurantDTO);
            }

            if (results.isEmpty()) {
                throw new SolrClientException(ErrorMessages.CANNOT_FETCH_DATA);
            }

            return restaurantDTOList;
        } catch (SolrServerException | java.io.IOException e) {
            throw new SolrInternalErrorException(ErrorMessages.SOLR_SERVER_ERROR);
        }
    }
}

