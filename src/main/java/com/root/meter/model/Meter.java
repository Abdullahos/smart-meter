package com.root.meter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Entity
@Data
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore //to avoid infinite recursion meter->user->meter->user->....
    private Users userId;

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
        this.userId = user;
    }

    public Meter() {
    }

}
