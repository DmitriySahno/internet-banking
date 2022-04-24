package com.sahd.Internetbanking.payload.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransferMoneyRequest {
    private final Long userFromId;
    private final Long userToId;
    private final Double amount;
}
