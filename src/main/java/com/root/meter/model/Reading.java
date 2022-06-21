package com.root.meter.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@Entity
/**
 * represent summation of readings per hour
 */
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "meterId")
    @JsonIgnore
    private Meter meter;
    private LocalDateTime date;
    private Double volt;
    private Double electric_current;
    private Double energy;    //kw
    private Double activePower;
    private Double reActivePower;
    private Double amount;   //cents per kW

    public Reading() {
    }

    public Reading(Meter meter, LocalDateTime date, Double volt, Double electric_current, Double energy, Double amount) {
        this.meter = meter;
        this.date = date;
        this.volt = volt;
        this.electric_current = electric_current;
        this.energy = energy;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getVolt() {
        return volt;
    }

    public void setVolt(Double volt) {
        this.volt = volt;
    }

    public Double getElectric_current() {
        return electric_current;
    }

    public void setElectric_current(Double electric_current) {
        this.electric_current = electric_current;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getActivePower() {
        return activePower;
    }

    public void setActivePower(Double activePower) {
        this.activePower = activePower;
    }

    public Double getReActivePower() {
        return reActivePower;
    }

    public void setReActivePower(Double reActivePower) {
        this.reActivePower = reActivePower;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
