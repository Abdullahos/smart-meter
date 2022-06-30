package com.root.meter.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReadingDTO {

    private Long meterId;
    @NotNull(message="timeStamp can't be null")
    private String date;
    @NotNull(message="volt can't be null")
    private Double volt;
    @NotNull(message="current can't be null")
    private Double electric_current;
    @NotNull(message="energy can't be null")
    private Double energy;
    @JsonIgnore
    private Double activePower;
    @JsonIgnore
    private Double reActivePower;
    private Double amount;   //cents per kW
}
