package com.root.meter.controller;

import com.root.meter.api.BillAPI;
import com.root.meter.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BillController {
    @Autowired
    private BillAPI billAPI;

    @GetMapping("me/generate/bill")
    public String generateBill(@RequestParam("userId")Long userId, Model model){
        ResponseEntity<Bill> billResponseEntity = billAPI.generateBill(userId);
        model.addAttribute("bill",billResponseEntity.getBody());
        model.addAttribute("userId", userId);
        return "bill";
    }
}
