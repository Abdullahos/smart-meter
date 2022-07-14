package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.DTO.PaymentView;
import com.root.meter.model.Bill;
import com.root.meter.model.Payment;
import com.root.meter.service.MeterService;
import com.root.meter.service.PaymentService;
import com.root.meter.service.StripeService;
import com.root.meter.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

/**
 * controller that will receive the POST request made by the checkout form and submit
 * the charge to Stripe via our StripeService.
 */
@Controller
@Transactional  //all or not
public class ChargeController {

    @Autowired
    private StripeService stripPaymentsService;
    @Autowired
    private MeterService meterService;
    @Autowired
    private PaymentService paymentService;


    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        System.out.println("charge. before charge");
        Charge charge = stripPaymentsService.charge(chargeRequest);  //perform charge on strip and get the result
        System.out.println("charge. after charge");

        //generate receipt and reset all the debt(amount$ and energy)
        PaymentView payment_receipt = paymentService.save(chargeRequest);
        model.addAttribute("payment", payment_receipt);
        System.out.println("charge. saved charge");

        return "paymentResult";
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<String> handleError(Model model, StripeException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}