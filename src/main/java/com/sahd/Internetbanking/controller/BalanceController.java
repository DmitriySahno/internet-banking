package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.entity.Balance;
import com.sahd.Internetbanking.payload.response.BalanceResponse;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import com.sahd.Internetbanking.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/getBalance/{userId}")
    public ResponseEntity<Object> getBalance(@PathVariable("userId") Long userId) {
        try {
            Balance balance = balanceService.getBalance(userId);
            return ResponseEntity.ok(new BalanceResponse(balance.getAmount()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(-1, e.getMessage()));
        }
    }

}

