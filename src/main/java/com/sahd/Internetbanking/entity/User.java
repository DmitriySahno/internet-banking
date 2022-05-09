package com.sahd.Internetbanking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //    @Column(unique = true, nullable = false, columnDefinition = "auto_increment")
    public static final String SEQUENCE_ID = "user_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
