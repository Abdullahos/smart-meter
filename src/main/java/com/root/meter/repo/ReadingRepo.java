package com.root.meter.repo;

import com.root.meter.DTO.ReadingDTO;
import com.root.meter.model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface ReadingRepo extends JpaRepository<Reading, Long> {
    Optional<List<Reading>> findAllByMeterId(Long meterId);

    Optional<List<Reading>> findAllByDateBetween(LocalDateTime start, LocalDateTime end);

    Optional<Reading> findAllByMeterIdAndDate(Long meterId, LocalDateTime date);

    @Query(value = "SELECT sum(energy) as e , sum(amount) as a , date FROM reading " +
                   "where meter_id = :meter_id AND (date between  :start AND :end) group by date", nativeQuery = true)
    List<Object> findLastWeekEnergyConsumptionByMeterId(Long meter_id, LocalDateTime start, LocalDateTime end);
}
