package com.root.meter.service;

import com.root.meter.DTO.EventDTO;
import com.root.meter.Exception.ObjectNotFoundException;
import com.root.meter.model.Event;
import com.root.meter.model.Meter;
import com.root.meter.repo.EventRepo;
import com.root.meter.repo.MeterRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional

public class EventService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private MeterRepo meterRepo;
    @Autowired
    private ReadingService readingService;

    public Event save(EventDTO dto){
        return eventRepo.save(toEvent(dto));
    }

    public EventDTO toDto(Event event){
        EventDTO dto = new EventDTO();
        BeanUtils.copyProperties(event,dto);
        //assign meter id
        dto.setMeterId(event.getMeterId().getId());
        dto.setDate(String.valueOf(event.getDate()));
        return dto;
    }
    public Event toEvent(EventDTO dto){
        Event event =  new Event();
        BeanUtils.copyProperties(dto,event);
        Optional<Meter> meterById = meterRepo.findById(dto.getMeterId());
        if(!meterById.isPresent())   throw new ObjectNotFoundException();
        else {
            event.setMeterId(meterById.get());
            event.setDate(readingService.STPMTimeStampToLocalDateTime(dto.getDate()));
        }
        return event;
    }
}
