package com.root.meter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Entity
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Users user;

    @OneToMany(mappedBy = "meter",fetch = FetchType.LAZY)  //meter must be the same spelling as the property in DailyReading class
    private List<Reading> readingList = new ArrayList<>();

    @OneToMany(mappedBy = "meter",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MonthlyConsumption> monthlyConsumptionList = new ArrayList<>();

    @OneToMany(mappedBy = "meter",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reading> ReadingList = new ArrayList<>();

    private Double debt = 0.0;
    private Double energyDebt = 0.0;

    public Meter(Users user) {
        this.user = user;
    }

    public Meter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Reading> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<Reading> readingList) {
        this.readingList = readingList;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getEnergyDebt() {
        return energyDebt;
    }

    public void setEnergyDebt(Double energyDebt) {
        this.energyDebt = energyDebt;
    }

    public List<MonthlyConsumption> getMonthlyConsumptionList() {
        return monthlyConsumptionList;
    }

    public void setMonthlyConsumptionList(List<MonthlyConsumption> monthlyConsumptionList) {
        this.monthlyConsumptionList = monthlyConsumptionList;
    }
}
