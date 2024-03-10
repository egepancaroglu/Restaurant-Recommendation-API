package com.egepancaroglu.userreviewservice.entity;

import com.egepancaroglu.userreviewservice.entity.enums.Rate;
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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate")
    private Rate rate;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
