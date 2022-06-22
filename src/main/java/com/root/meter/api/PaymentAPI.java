package com.root.meter.api;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.DTO.PaymentView;
import com.root.meter.model.Payment;
import com.root.meter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payment")
@Transactional
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/save")
    public PaymentView save(@RequestBody ChargeRequest chargeRequest){
        return paymentService.save(chargeRequest);
    }

    @GetMapping("get/betweenDates")
    public List<PaymentView> get(@RequestParam("userId") Long userId,
                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        return paymentService.getPaymentsBetween2DatesByUserId(userId, start, end);
    }
    @GetMapping("get/by/date")
    public List<PaymentView> get(@RequestParam("userId") Long userId,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return paymentService.getPaymentsOfGiveDateAndUserId(userId,date);
    }
    @GetMapping("get/by/userId")
    public PaymentView getLastPaymentByUserId(@RequestParam("userId")Long userId){
        return paymentService.getLastPaymentOfUser(userId);
    }
}