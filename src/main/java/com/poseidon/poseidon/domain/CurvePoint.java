package com.poseidon.poseidon.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Entity
@DynamicUpdate
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    //@DecimalMin(value = "0", inclusive = false, message = "must not be null")
    @NotNull(message = "Must not be null")
    @DecimalMin(value = "0", inclusive = false, message = "CurveId must be positive")
    @Column(name = "CurveId")
    private Integer curveId;

    @Column(name = "asOfDate")
    private LocalDate asOfDate;

    @DecimalMin(value = "0", inclusive = false, message = "Term must be positive")
    @NotNull(message = "Term is mandatory")
    @Column(name = "term")
    private Double term;

    @DecimalMin(value = "0", inclusive = false, message = "Value must be positive")
    @NotNull(message = "Value is mandatory")
    @Column(name = "value")
    private Double value;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public LocalDate getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(LocalDate asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
