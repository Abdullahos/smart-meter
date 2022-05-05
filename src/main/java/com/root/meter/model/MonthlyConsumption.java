package com.root.meter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.YearMonth;

@Transactional
@Entity
public class MonthlyConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "meterId")
    @JsonIgnore
    private Meter meter;
    private YearMonth yearMonth;
    private Double amount;  //$
    private Double energy;  //kwh
    private Double volt;
    private Double electric_current;

    public MonthlyConsumption(Meter meter,Double amount, Double energy, Double volt, Double current, YearMonth yearMonth) {
        this.meter = meter;
        this.amount = amount;
        this.yearMonth = yearMonth;
        this.energy = energy;
        this.volt = volt;
        this.electric_current = current;
    }

    public MonthlyConsumption(Meter meter,Long id, Double amount, Double energy, Double volt, Double current, YearMonth yearMonth) {
        this.meter = meter;
        this.id = id;
        this.yearMonth = yearMonth;
        this.amount = amount;
        this.energy = energy;
        this.volt = volt;
        this.electric_current = current;
    }

    public MonthlyConsumption() {
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

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }
}
