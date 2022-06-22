package com.root.meter.service;

import com.root.meter.DTO.MeterDTO;
import com.root.meter.Exception.ObjectNotFoundException;
import com.root.meter.model.Meter;
import com.root.meter.repo.MeterRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeterService {
    @Autowired
    private MeterRepo meterRepo;
    @Autowired
    private UserService userService;

    public Long save(Meter meter){
        return meterRepo.save(meter).getId();
    }

    public Meter findById(Long id){
        Optional<Meter> optionalMeterById = meterRepo.findById(id);
        if(optionalMeterById.isPresent())   return optionalMeterById.get();
        throw new ObjectNotFoundException("no meter found to the id: "+id);
    }
    public double getDebtOfMeterByMeterId(Long meterId){
        Optional<Meter> meter = meterRepo.findById(meterId);
        if(meter.isPresent())    return meter.get().getDebt();
        throw new ObjectNotFoundException("no meter found to the id: "+meterId);
    }

    public void resetDebt(Long userId) {
        Meter meter = meterRepo.findByUserId(userId);
        meter.setDebt(0.0);
        meterRepo.save(meter);
        //TODO: exception
    }

    public void resetEnergyDebt(Long userId) {
        Meter meter = meterRepo.findByUserId(userId);
        meter.setEnergyDebt(0.0);
        meterRepo.save(meter);

    }
        //TODO: exception

    public MeterDTO findByUserId(Long userId) {
        return toDto(meterRepo.findByUserId(userId));
    }

    public Meter dtoToMeter(MeterDTO dto){
        Meter meter = new Meter();
        BeanUtils.copyProperties(dto,meter);
        meter.setUser(userService.findUserByMeterId(dto.getId()));
        return meter;
    }
    public MeterDTO toDto(Meter meter){
        MeterDTO dto = new MeterDTO();
        BeanUtils.copyProperties(meter,dto);
        if(meter.getUser()!=null){
            dto.setUserId(meter.getUser().getId());
        }
        return dto;
    }
}
