package com.root.meter.model;

import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Data
@Transactional
public class Reset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;



}
