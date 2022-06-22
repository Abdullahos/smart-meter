package com.root.meter.service;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.DTO.PaymentDTO;
import com.root.meter.DTO.PaymentView;
import com.root.meter.model.Payment;
import com.root.meter.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private MeterService meterService;

    public PaymentView save(ChargeRequest chargeRequest){
        String amount = chargeRequest.getAmount();
        Payment payment = new Payment(chargeRequest.getResetNo(),new Double(amount), userService.findById(chargeRequest.getUserId()),LocalDate.now());
        //reset debt of meter if success
        meterService.resetDebt(chargeRequest.getUserId());
        meterService.resetEnergyDebt(chargeRequest.getUserId());
        Payment savedPayment = paymentRepo.save(payment);
        PaymentView paymentView = new PaymentDTO(savedPayment.getAmount(),savedPayment.getPaymentDate(),savedPayment.getResetNo());
        return paymentView;
    }

    public List<PaymentView> getPaymentsBetween2DatesByUserId(Long userId, LocalDate start, LocalDate end) {
        return paymentRepo.findAmountAndPaymentDateByUsersIdAndPaymentDateBetween(userId, start, end);
    }
    public List<PaymentView> getPaymentsOfGiveDateAndUserId(Long userId, LocalDate date){
        return paymentRepo.findByUsersIdAndPaymentDate(userId, date);
    }

    public Payment save(Payment payment) {
        return paymentRepo.save(payment);
    }

    public PaymentView getLastPaymentOfUser(Long userId){
        return paymentRepo.findFirstPaymentByUsersIdOrderByPaymentDateDesc(userId);
    }
}

