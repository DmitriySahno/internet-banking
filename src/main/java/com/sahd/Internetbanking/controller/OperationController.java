package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.entity.Operation;
import com.sahd.Internetbanking.payload.request.BaseRequest;
import com.sahd.Internetbanking.payload.request.OperationListRequest;
import com.sahd.Internetbanking.payload.request.TransferMoneyRequest;
import com.sahd.Internetbanking.payload.response.BalanceResponse;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import com.sahd.Internetbanking.service.BalanceService;
import com.sahd.Internetbanking.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/takeMoney")
    public ResponseEntity<Object> takeMoney(@RequestBody BaseRequest request){
        try {
            operationService.takeMoney(request.getUserId(), request.getAmount());
            return ResponseEntity.ok(new BalanceResponse(1d));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @PostMapping("/putMoney")
    public ResponseEntity<Object> putMoney(@RequestBody BaseRequest request){
        try {
            operationService.putMoney(request.getUserId(), request.getAmount());
            return ResponseEntity.ok(new BalanceResponse(1d));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<Object> transferMoney(@RequestBody TransferMoneyRequest request) {
        try {
            operationService.transferMoney(request.getUserFromId(), request.getUserToId(), request.getAmount());
            return ResponseEntity.ok(new BalanceResponse(1d));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @GetMapping("/getOperationList")
    public ResponseEntity<Object> getOperationList(@RequestBody OperationListRequest request) {
        try {
            List<Operation> operations = operationService.getOperations(request.getUserId(), request.getDateFrom(), request.getDateTo());
            return ResponseEntity.ok(operations);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

}
