package com.root.meter.service;

import com.root.meter.DTO.MonthlyConsumptionDTO;
import com.root.meter.model.Reading;
import com.root.meter.model.Meter;
import com.root.meter.model.MonthlyConsumption;
import com.root.meter.repo.MonthlyConsumptionRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

@Service
@Transactional
public class MonthlyConsumptionService {
    @Autowired
    private MonthlyConsumptionRepo consumptionRepo;

    public MonthlyConsumption save(Reading reading){

        //get the yearMonth of this consumption
        LocalDateTime localDateTime = reading.getDate();
        YearMonth yearMonth = YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
        MonthlyConsumption monthlyConsumption;
        //check if it's the first reading in the month
        Optional<MonthlyConsumption> optionalConsumption = consumptionRepo.findByMeterIdAndYearMonth(reading.getMeter().getId(), yearMonth);
        //get
        Double energy = reading.getEnergy();
        Double volt = reading.getVolt();
        Double current = reading.getElectric_current();
        System.out.println("current: "+current);
        Double amount = reading.getAmount();
        Meter meter = reading.getMeter();
        //not first consumption
        if(optionalConsumption.isPresent()){
            //get the consumption of this month
            MonthlyConsumption consumption = optionalConsumption.get();
            energy += consumption.getEnergy();
            volt += consumption.getVolt();
            current += consumption.getElectric_current();
            amount += consumption.getAmount();
             monthlyConsumption= new MonthlyConsumption(meter,consumption.getId(),amount,energy,volt,current,yearMonth);
        }
        else {
             monthlyConsumption = new MonthlyConsumption(meter,amount,energy,volt,current,yearMonth);
        }
        return consumptionRepo.save(monthlyConsumption);
    }
    public MonthlyConsumptionDTO toDto(MonthlyConsumption consumption){
        MonthlyConsumptionDTO dto = new MonthlyConsumptionDTO();
        BeanUtils.copyProperties(consumption,dto);
        return dto;
    }
    public MonthlyConsumption fromDto(MonthlyConsumptionDTO dto){
        MonthlyConsumption consumption = new MonthlyConsumption();
        BeanUtils.copyProperties(dto, consumption);
        return consumption;
    }


    public MonthlyConsumption get(Long meterId, YearMonth yearMonth) {
        Optional<MonthlyConsumption> optionalConsumption = consumptionRepo.findByMeterIdAndYearMonth(meterId, yearMonth);
        if(optionalConsumption.isPresent()){
            return optionalConsumption.get();
        }
        else {
            return null;
        }
    }
}
