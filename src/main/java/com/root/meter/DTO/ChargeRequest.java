package com.root.meter.DTO;

import lombok.Data;

/**
 * POJO that we will use as a business entity during the charge operation:
 */
@Data
public class ChargeRequest {
    private Long userId;
    public enum Currency {
        EUR, USD;
    }
    private String description;
    private String amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
    private Long resetNo;

}