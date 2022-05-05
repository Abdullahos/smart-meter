package com.root.meter.api;

import com.root.meter.DTO.EventDTO;
import com.root.meter.model.Event;
import com.root.meter.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
@RestController
@Transactional
@RequestMapping("/event")
public class EventAPI {
    @Autowired
    private EventService eventService;

    @PostMapping("/post")
    public Event save(@RequestBody EventDTO event){
        return eventService.save(event);
    }
}
