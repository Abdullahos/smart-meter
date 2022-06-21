package com.root.meter.DTO;

import java.time.LocalDate;

public interface PaymentView {
    public Double getAmount();
    public LocalDate getPaymentDate();
}
