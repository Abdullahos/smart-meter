package com.root.meter.api;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.model.Bill;
import com.root.meter.model.Reading;
import com.root.meter.model.Users;
import com.root.meter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BillAPI {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private UserService userService;
    @GetMapping("generate/Bill")
    public ResponseEntity<Bill> generateBill(@RequestParam Long userId){
        Users user = userService.findById(userId);
        double debt = user.getMeter().getDebt();
        Double energyDebt = user.getMeter().getEnergyDebt();
        return new ResponseEntity<Bill>(new Bill(userService.userToDTO(user), energyDebt, debt, LocalDate.now()), HttpStatus.OK);
    }

}
