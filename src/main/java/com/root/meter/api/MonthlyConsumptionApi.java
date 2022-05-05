package com.root.meter.api;

import com.root.meter.Exception.ObjectNotFoundException;
import com.root.meter.model.MonthlyConsumption;
import com.root.meter.service.MonthlyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/consumption")
public class MonthlyConsumptionApi {
    @Autowired
    private MonthlyConsumptionService monthlyConsumptionService;

    @GetMapping("/get")
    public ResponseEntity<MonthlyConsumption> get( @RequestParam Long meterID,@RequestParam YearMonth yearMonth){
        MonthlyConsumption monthlyConsumption = monthlyConsumptionService.get(meterID, yearMonth);
        if (monthlyConsumption != null) {
            return new ResponseEntity<MonthlyConsumption>(monthlyConsumption, HttpStatus.OK);
        }
        else {
            throw new ObjectNotFoundException();
        }
    }
}
