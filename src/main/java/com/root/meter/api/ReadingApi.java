package com.root.meter.api;

import com.root.meter.DTO.EnergyView;
import com.root.meter.DTO.ReadingDTO;
import com.root.meter.model.Reading;
import com.root.meter.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Transactional
@RestController
@RequestMapping("/reading")
public class ReadingApi {
    @Autowired
    private ReadingService readingService;
    @PostMapping("/post")
    public ResponseEntity<Reading> save(@RequestBody ReadingDTO readingDTO){
        Reading reading = readingService.save(readingDTO);
        if(reading != null){
            return new ResponseEntity<Reading>(reading, HttpStatus.CREATED);
        }
        else {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * return all readings by meter id
     * @param meterId
     * @return
     */
    @GetMapping("/get")
    public List<ReadingDTO> getReadingById(@RequestParam Long meterId){
        return readingService.getReadingsByMeterId(meterId);
    }

    /**
     * retrieve all readings of given hour
     * @return
     */
    @GetMapping("/get/period")
    public List<EnergyView> getReadingBetweenTwoTimeStamps(
            @RequestParam("meterId")Long meterId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        return  readingService.getEnergyAndAmountBetween2DatesByMeterId(meterId, start.atStartOfDay(), end.atStartOfDay());
    }
    //return the readings of the last week
    @GetMapping("/getReadingsOf/last/week")
    public List<EnergyView> getReadingsOfLastWeek(@RequestParam("meterId")Long meterId){
        return  readingService.getEnergyAndAmountBetween2DatesByMeterId(meterId,LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
    }
}
