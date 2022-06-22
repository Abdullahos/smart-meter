package com.root.meter.DTO;

import com.stripe.model.Charge;

import java.time.LocalDate;

public interface PaymentView {
    public Long getResetNo();
    public Double getAmount();
    public LocalDate getPaymentDate();
}
