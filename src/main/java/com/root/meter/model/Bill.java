package com.root.meter.model;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.time.LocalDate;
@Data
//we don't save bill in db, it's only for displaying to user, after it paid, we save the payment
public class Bill {
    private Users users;
    private double energy;
    private double amount;
    private LocalDate lastPaymentDate;

    public Bill(){

    }

    public Bill(Users users, double energy, double amount, LocalDate lastPaymentDate) {
        this.users = users;
        this.energy = energy;
        this.amount = amount;
        this.lastPaymentDate = lastPaymentDate;
    }
}
