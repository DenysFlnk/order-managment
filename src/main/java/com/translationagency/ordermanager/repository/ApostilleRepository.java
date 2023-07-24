package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Apostille;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApostilleRepository extends JpaRepository<Apostille, Integer> {

    List<Apostille> getAllByOrderId(int orderId);
}
