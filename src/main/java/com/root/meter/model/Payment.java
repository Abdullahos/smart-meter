package com.root.meter.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private double amount;  //cents
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private LocalDate localDate;

    public Payment() {
    }

    public Payment(double amount, Users userId, LocalDate localDate) {
        this.amount = amount;
        this.users = userId;
        this.localDate = localDate;
    }

}
