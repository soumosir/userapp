package com.soumosir.userapp;


import com.soumosir.userapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepository extends JpaRepository<User,Long> {
}
