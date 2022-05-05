package com.root.meter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Users userId;

    private LocalDate localDate;

    public Payment() {
    }

    public Payment(double amount, Users userId, LocalDate localDate) {
        this.amount = amount;
        this.userId = userId;
        this.localDate = localDate;
    }

}
