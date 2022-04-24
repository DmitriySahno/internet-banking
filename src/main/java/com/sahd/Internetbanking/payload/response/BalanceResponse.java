package com.sahd.Internetbanking.payload.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BalanceResponse {
    private final Double value;
}
