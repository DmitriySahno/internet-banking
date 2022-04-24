package com.sahd.Internetbanking.payload.response;

import com.sahd.Internetbanking.enums.OperationType;

import java.time.LocalDateTime;
import java.util.List;

public class OperationListResponse {
    private List<OperationResponse> operationList;

    private static class OperationResponse{
        private LocalDateTime date;
        private OperationType type;
        private Double amount;
    }
}
