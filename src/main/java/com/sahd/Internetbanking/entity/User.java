package com.sahd.Internetbanking.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
