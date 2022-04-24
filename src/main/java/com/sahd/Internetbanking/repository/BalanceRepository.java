package com.sahd.Internetbanking.repository;

import com.sahd.Internetbanking.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
