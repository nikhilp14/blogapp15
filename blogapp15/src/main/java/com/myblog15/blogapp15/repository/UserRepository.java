package com.myblog15.blogapp15.repository;

import com.myblog15.blogapp15.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {// Here we are giving different different method names and performing a search with different parameter
    Optional<User> findByEmail(String email); // findBy return Optional Object.
    Optional<User> findByUsernameOrEmail(String username, String email); //If username = mike or email = mike14@gmail.com if it's find the record if it's exist then it will return the Optional object.
    Optional<User> findByUsername(String username);// Here you are finding, retrieving and returning an Object.
    Boolean existsByUsername(String username);// Here we just checking the Item.
    Boolean existsByEmail(String email);// If its existsBy it will return true, does not exit it will return false.

}
