package com.sahd.Internetbanking.entity;

import com.sahd.Internetbanking.enums.OperationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table
@Entity(name = "operations")
@NoArgsConstructor
@RequiredArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @NonNull
    private OperationType type;
    @NonNull
    private Double amount;
    @NonNull
    private LocalDateTime date;
}
