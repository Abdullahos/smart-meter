package com.root.meter.repo;

import com.root.meter.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepo extends CrudRepository<Payment,Long> {

}
