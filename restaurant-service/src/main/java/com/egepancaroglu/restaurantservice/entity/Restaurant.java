package com.egepancaroglu.restaurantservice.entity;

import com.egepancaroglu.restaurantservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.UUID;

/**
 * @author egepancaroglu
 */

@Getter
@Setter
@AllArgsConstructor
@SolrDocument(collection = "restaurants")
public class Restaurant {

    @Id
    @Field
    private String id;

    @Field
    private String name;

    @Field
    private String location;

    @Field
    private Double averageScore;

    @Field
    private Status status;

    public Restaurant() {
        this.id = generateUniqueId();
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }


}
