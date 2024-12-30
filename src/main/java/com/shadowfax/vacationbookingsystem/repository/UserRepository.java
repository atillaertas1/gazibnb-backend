package com.shadowfax.vacationbookingsystem.repository;

import com.shadowfax.vacationbookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "CALL update_user(:userId, :username, :email, :password)", nativeQuery = true)
    void updateUserByProcedure(
            @Param("userId") Long userId,
            @Param("username") String username,
            @Param("email") String email,
            @Param("password") String password
    );
}
