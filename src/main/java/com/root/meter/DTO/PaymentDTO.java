package com.root.meter.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements PaymentView{
    private Double amount;
    private LocalDate paymentDate;
    private Long resetNo;
    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

}
