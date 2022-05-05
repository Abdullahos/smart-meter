package com.root.meter.repo;

import com.root.meter.model.MonthlyConsumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyConsumptionRepo extends CrudRepository<MonthlyConsumption, Long> {
    Optional<MonthlyConsumption> findByMeterId(Long id);

    Optional<MonthlyConsumption> findByMeterIdAndYearMonth(Long meterId, YearMonth yearMonth);
}
