package com.ecomm_app.repository;

import com.ecomm_app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer>
{

    @Query(value = "select u from Users u where email=:email and password=:password")
    Optional<Users> findUserByUsernameAndPassword(String email, String password);

    @Query(value = "select u from Users u where u.name = :username")
    Optional<Users> findByUsername(String username);
    @Query(value = "select u from Users u where u.email = :email")
    Optional<Users> findByEmail(String email);
}
