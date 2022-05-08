package com.root.meter.api;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.model.Payment;
import com.root.meter.service.MeterService;
import com.root.meter.service.PaymentService;
import com.root.meter.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * controller that will receive the POST request made by the checkout form and submit
 * the charge to Stripe via our StripeService.
 */
@RestController
@Transactional  //all or not
public class ChargeAPI {

    @Autowired
    private StripeService stripPaymentsService;
    @Autowired
    private MeterService meterService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<Payment> charge(ChargeRequest chargeRequest)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = stripPaymentsService.charge(chargeRequest);  //perform charge on strip and get the result
        //generate receipt and reset all the debt(amount$ and energy)
        Payment payment_receipt = paymentService.save(chargeRequest);
        return new ResponseEntity<>(payment_receipt, HttpStatus.OK);
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<String> handleError(Model model, StripeException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}