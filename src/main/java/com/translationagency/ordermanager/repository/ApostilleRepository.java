package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Apostille;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApostilleRepository extends JpaRepository<Apostille, Integer> {

    List<Apostille> getAllByOrderId(int orderId);

    Optional<Apostille> getApostilleByIdAndOrderId(int apostilleId, int orderId);
}
