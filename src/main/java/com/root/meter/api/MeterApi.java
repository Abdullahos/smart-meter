package com.root.meter.api;

import com.root.meter.DTO.MeterDTO;
import com.root.meter.model.Meter;
import com.root.meter.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/meter")
public class MeterApi {
    @Autowired
    public MeterService meterService;
    @PostMapping("/save")
    public ResponseEntity<Long> save(@Valid @RequestBody Meter meter){
        Long meterID = meterService.save(meter);
        if( meterID!= null ){
            return new ResponseEntity<Long>(meterID, HttpStatus.CREATED);
        }
        //TODO: implement the rest errors
        else{
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/get")
    public ResponseEntity<MeterDTO> get(@RequestParam Long meterId){
        Meter meter = meterService.findById(meterId);
        return ResponseEntity.ok(meterService.toDto(meter));
    }
    //gsoap
    @GetMapping("/getFromMeter")
    public Mono<String> getFromMeter() {
        //Meter meter = meterService.findById(meterId);
        //TODO: check if meter exist
        //call meter server to get the response
        WebClient webClient = WebClient.create("http://localhost:8080");
        return webClient.get().retrieve().bodyToMono(String.class);
    }
    @GetMapping("get/by/userid")
    public MeterDTO getByUserId(@RequestParam("userId")Long userId){
        return meterService.findByUserId(userId);
    }
}
