package com.root.meter.repo;

import com.root.meter.model.Meter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Transactional
@Repository
public interface MeterRepo extends CrudRepository<Meter, Long > {
    Optional<Double> findDebtById(Long id);

    Optional<Meter> findByUserIdId(Long userId);
}
