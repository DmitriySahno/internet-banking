package com.sahd.Internetbanking.payload.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BaseRequest {
    private final Long userId;
    private final Double amount;
}
