package com.sahd.Internetbanking.repository;

import com.sahd.Internetbanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByName(String name);
}
