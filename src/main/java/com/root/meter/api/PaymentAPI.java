package com.root.meter.api;

import com.root.meter.DTO.PaymentView;
import com.root.meter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("payment/")
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("get/betweenDates")
    public List<PaymentView> get(@RequestParam("userId") Long userId,
                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        return paymentService.getPaymentsBetween2DatesByUserId(userId, start, end);
    }
    @GetMapping("get/by/date")
    public List<PaymentView> get(@RequestParam("userId") Long userId,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return paymentService.getPaymentsOfGiveDateAndUserId(userId,date);
    }
}