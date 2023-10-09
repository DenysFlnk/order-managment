package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.repository.ApostilleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@Service
@AllArgsConstructor
public class ApostilleService {

    private ApostilleRepository apostilleRepository;

    private OrderService orderService;

    public List<Apostille> getAllByOrder(int orderId) {
        return apostilleRepository.getAllByOrderId(orderId);
    }

    public Apostille get(int orderId, int apostilleId) {
        return checkNotFoundWithId(apostilleRepository.getApostilleByIdAndOrderId(apostilleId, orderId).orElse(null),
                apostilleId);
    }

    public void create(Apostille apostille, int orderId) {
        checkNew(apostille);
        apostille.setOrder(orderService.getReference(orderId));
        apostilleRepository.save(apostille);
        orderService.recalculateOrderCostAndSave(orderId);
    }

    public void update(Apostille apostille, int orderId) {
        apostille.setOrder(orderService.getReference(orderId));
        apostilleRepository.save(apostille);
        orderService.recalculateOrderCostAndSave(apostille.getOrder().getId());
    }

    public void delete(int orderId, int apostilleId) {
        Apostille delete = checkNotFoundWithId(apostilleRepository.getApostilleByIdAndOrderId(apostilleId, orderId)
                .orElse(null), apostilleId);
        apostilleRepository.delete(delete);
        orderService.recalculateOrderCostAndSave(delete.getOrder().getId());
    }

}
