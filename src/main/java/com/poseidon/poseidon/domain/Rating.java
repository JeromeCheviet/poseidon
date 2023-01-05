package com.poseidon.poseidon.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Class use to connect data of table rating into an object
 */
@Entity
@DynamicUpdate
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @NotBlank(message = "MoodysRating is mandatory")
    @Column(name = "moodysRating")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    @Column(name = "sandPRating")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    @Column(name = "fitchRating")
    private String fitchRating;

    @NotNull(message = "Order is mandatory")
    @DecimalMin(value = "0", inclusive = false, message = "Order number must be positive")
    @Column(name = "orderNumber")
    private Integer orderNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
