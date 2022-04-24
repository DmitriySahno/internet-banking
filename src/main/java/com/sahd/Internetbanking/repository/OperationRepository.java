package com.sahd.Internetbanking.repository;

import com.sahd.Internetbanking.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
