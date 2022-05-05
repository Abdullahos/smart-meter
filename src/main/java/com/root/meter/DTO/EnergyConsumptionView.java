package com.root.meter.DTO;

import java.time.LocalDateTime;

public interface EnergyConsumptionView {
    public Double getEnergy();
    public LocalDateTime getDate();
    public Double getAmount();
}
