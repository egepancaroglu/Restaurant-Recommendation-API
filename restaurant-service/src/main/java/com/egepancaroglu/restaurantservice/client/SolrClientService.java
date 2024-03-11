package com.egepancaroglu.restaurantservice.client;

import com.egepancaroglu.restaurantservice.entity.Restaurant;
import com.egepancaroglu.restaurantservice.exception.SolrClientException;
import com.egepancaroglu.restaurantservice.general.ErrorMessages;
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
@SuppressWarnings("deprecation")
public class SolrClientService {

    @Value("${solr.client.url}")
    private String solrUrl;

    public List<Restaurant> performSolrQuery(String userLocation) {
        try (HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build()) {

            final Map<String, String> queryParamMap = new HashMap<>();

            queryParamMap.put("q", "*:*");
            queryParamMap.put("fq", "fq={!geofilt pt=" + userLocation + " sfield=location d=10}");
            queryParamMap.put("start", "0");
            queryParamMap.put("rows", "3");
            queryParamMap.put("sort", "sum(mul(div(averageScore,5),7),mul(div(sub(10,geodist(" + userLocation + ",location)),10),3)) desc");

            MapSolrParams queryParams = new MapSolrParams(queryParamMap);
            QueryResponse queryResponse = solrClient.query(queryParams);
            SolrDocumentList results = queryResponse.getResults();

            List<Restaurant> restaurantList = new ArrayList<>();
            for (SolrDocument solrDocument : results) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(solrDocument.get("id").toString());
                restaurant.setName(solrDocument.get("name").toString());
                restaurant.setLocation(solrDocument.get("location").toString());
                restaurant.setAverageScore((Double) solrDocument.get("averageScore"));
                restaurantList.add(restaurant);
            }

            return restaurantList;
        } catch (SolrServerException | java.io.IOException e) {
            throw new SolrClientException(ErrorMessages.CANNOT_FETCH_DATA); // or handle the exception as needed
        }
    }
}
