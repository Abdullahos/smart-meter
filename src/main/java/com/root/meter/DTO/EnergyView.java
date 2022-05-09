package com.root.meter.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EnergyView {
    private LocalDate date;
    private Double energy;
    private Double amount;

    public EnergyView(LocalDateTime date, Double energy, Double amount) {
        this.date = date.toLocalDate();
        this.energy = energy;
        this.amount = amount;
    }

    public EnergyView() {
    }
}
