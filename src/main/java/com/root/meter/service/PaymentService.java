package com.root.meter.service;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.model.Payment;
import com.root.meter.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private MeterService meterService;

    public Payment save(ChargeRequest chargeRequest){
        String amount = chargeRequest.getAmount();
        Payment payment = new Payment(new Double(amount), userService.findById(chargeRequest.getUserId()), LocalDate.now());
        //reset debt of meter if success
        meterService.resetDebt(chargeRequest.getUserId());
        meterService.resetEnergyDebt(chargeRequest.getUserId());
        return paymentRepo.save(payment);
    }
}
