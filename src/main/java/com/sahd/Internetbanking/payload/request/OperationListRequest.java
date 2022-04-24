package com.sahd.Internetbanking.payload.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class OperationListRequest {
    private final Long userId;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

}
