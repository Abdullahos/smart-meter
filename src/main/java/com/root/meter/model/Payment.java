package com.root.meter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long resetNo;
    private double amount;  //cents
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private LocalDate paymentDate;

    public Payment(Long resetNo, double amount, Users users, LocalDate paymentDate) {
        this.resetNo = resetNo;
        this.amount = amount;
        this.users = users;
        this.paymentDate = paymentDate;
    }
}
