package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.repository.ApostilleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApostilleService {

    private ApostilleRepository apostilleRepository;

    private OrderService orderService;

    public List<Apostille> getAllByOrder(int orderId) {
        return apostilleRepository.getAllByOrderId(orderId);
    }

    public Apostille get(int orderId, int apostilleId) {
        return apostilleRepository.getApostilleByIdAndOrderId(apostilleId, orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public void create(Apostille apostille, int orderId) {
        apostille.setOrder(orderService.getReference(orderId));
        apostilleRepository.save(apostille);
        orderService.recalculateOrderCost(orderId);
    }

    public void update(Apostille apostille, int orderId) {
        apostille.setOrder(orderService.getReference(orderId));
        apostilleRepository.save(apostille);
        orderService.recalculateOrderCost(apostille.getOrder().getId());
    }

    public void delete(int orderId, int apostilleId) {
        Apostille delete = apostilleRepository.getApostilleByIdAndOrderId(apostilleId, orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        apostilleRepository.delete(delete);
        orderService.recalculateOrderCost(delete.getOrder().getId());
    }

}
