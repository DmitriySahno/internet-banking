package com.sahd.Internetbanking.repository;

import com.sahd.Internetbanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
