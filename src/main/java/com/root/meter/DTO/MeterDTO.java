package com.root.meter.DTO;

import com.root.meter.model.Reading;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterDTO {
    private Long id;
    private Long userId;
    private List<Reading> readingList;
    private Double debt = 0.0;
    private Double energyDebt = 0.0;


}
