package com.sahd.Internetbanking.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity(name = "balance")
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    @Id
    private Long userId;
    private Double amount;
}
