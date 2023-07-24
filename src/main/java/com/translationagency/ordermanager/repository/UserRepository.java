package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
