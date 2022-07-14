package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.api.BillAPI;
import com.root.meter.model.Bill;
import com.root.meter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BillController {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private UserService userService;

    @Autowired
    private BillAPI billAPI;

    @GetMapping("me/generate/bill")
    public String generateBill(@RequestParam("userId")Long userId, Model model){
        //get the amount to be charged
        double amount = userService.getUserConsumption(userId); //in dollars
        amount *=  100;
        ResponseEntity<Bill> billResponseEntity = billAPI.generateBill(userId);
        model.addAttribute("bill",billResponseEntity.getBody());
        model.addAttribute("userId", userId);
        model.addAttribute("amount", (int)amount); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "bill";
    }

}
