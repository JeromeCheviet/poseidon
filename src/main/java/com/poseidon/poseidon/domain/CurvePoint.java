package com.poseidon.poseidon.domain;

// import org.hibernate.validator.constraints.Length;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@DynamicUpdate
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @DecimalMin(value = "0", inclusive = false, message = "Curve ID must be positive")
    @Column(name = "CurveId")
    private int curveId;

    @Column(name = "asOfDate")
    private LocalDate asOfDate;

    @DecimalMin(value = "0", inclusive = false, message = "Term must be positive")
    @Column(name = "term")
    private double term;

    @DecimalMin(value = "0", inclusive = false, message = "Value must be positive")
    @Column(name = "value")
    private double value;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurveId() {
        return curveId;
    }

    public void setCurveId(int curveId) {
        this.curveId = curveId;
    }

    public LocalDate getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(LocalDate asOfDate) {
        this.asOfDate = asOfDate;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
