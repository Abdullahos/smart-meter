package com.root.meter.repo;

import com.root.meter.DTO.PaymentView;
import com.root.meter.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepo extends CrudRepository<Payment,Long> {

    List<PaymentView> findAmountAndPaymentDateByUsersIdAndPaymentDateBetween(Long userId, LocalDate start, LocalDate end);

    List<PaymentView> findByUsersIdAndPaymentDate(Long userId, LocalDate date);

    PaymentView findFirstPaymentByUsersIdOrderByPaymentDateDesc(Long userId);        //findFirstByOrderBySeatNumberAsc
}
