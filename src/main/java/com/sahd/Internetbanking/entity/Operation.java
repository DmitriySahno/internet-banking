package com.sahd.Internetbanking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sahd.Internetbanking.enums.OperationType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table
@Entity(name = "operations")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Operation {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NonNull
    private OperationType type;
    @NonNull
    private Double amount;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
