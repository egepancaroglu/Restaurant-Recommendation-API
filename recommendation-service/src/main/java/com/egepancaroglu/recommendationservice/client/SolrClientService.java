package com.egepancaroglu.recommendationservice.client;

import com.egepancaroglu.recommendationservice.dto.RestaurantResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author egepancaroglu
 */

@Service
@SuppressWarnings("deprecation")
public class SolrClientService {

    private final String solrUrl = "http://localhost:8983/solr/restaurants"; // Solr server URL

    public List<RestaurantResponse> performSolrQuery() {
        try (HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build()) {

            final Map<String, String> queryParamMap = new HashMap<>();
            queryParamMap.put("q", "*:*");
            queryParamMap.put("fq", "fq={!geofilt pt=40.171830889748826,29.089459446217543 sfield=location d=10}");
            queryParamMap.put("start", "0");
            queryParamMap.put("rows", "3");
            queryParamMap.put("sort", "sum(mul(averageScore,14),mul(sub(10,geodist(40.171830889748826,29.089459446217543,location)),3)) desc");

            MapSolrParams queryParams = new MapSolrParams(queryParamMap);
            QueryResponse queryResponse = solrClient.query(queryParams);
            SolrDocumentList results = queryResponse.getResults();

            List<RestaurantResponse> restaurantResponseList = new ArrayList<>();
            for (SolrDocument solrDocument : results) {
                RestaurantResponse restaurantResponse = new RestaurantResponse(
                        solrDocument.get("id").toString(),
                        solrDocument.get("name").toString(),
                        solrDocument.get("location").toString(),
                        (Double) solrDocument.get("averageScore")
                );
                restaurantResponseList.add(restaurantResponse);
            }

            return restaurantResponseList;
        } catch (SolrServerException | java.io.IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // or handle the exception as needed
        }
    }
}
