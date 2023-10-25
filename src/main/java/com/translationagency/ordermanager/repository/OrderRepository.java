package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT or FROM Order or ORDER BY or.id DESC")
    List<Order> getAll();

    @Query("SELECT or FROM Order or  LEFT JOIN FETCH or.documents ORDER BY or.id DESC")
    List<Order> getAllWithDocument(Pageable pageable);

    @Query("SELECT or FROM Order or LEFT JOIN FETCH or.documents doc WHERE or.id = ?1")
    Optional<Order> getWithDocument(int id);
}
