package com.sahd.Internetbanking.repository;

import com.sahd.Internetbanking.entity.Operation;
import com.sahd.Internetbanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByUserIdAndDateBetween(User user, LocalDateTime start, LocalDateTime end);
}
