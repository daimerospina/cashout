package com.cashout.demo.domain.cashout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashoutRequest {
    private String userId;
    private long amount;
}
