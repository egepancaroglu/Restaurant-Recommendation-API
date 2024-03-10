package com.egepancaroglu.userreviewservice.entity;

import com.egepancaroglu.userreviewservice.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author egepancaroglu
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private Status state;

    @Column(name = "district")
    private String district;

    @Column(name = "street")
    private String street;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
