package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.repository.ApostilleRepository;
import com.translationagency.ordermanager.repository.OrderRepository;
import com.translationagency.ordermanager.util.OrderUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private ApostilleRepository apostilleRepository;

    public List<Order> getAllWithDocument(Pageable pageable) {
        return orderRepository.getAllWithDocument(pageable);
    }

    public int getAllCount() {
        return orderRepository.getAll().size();
    }

    public Order getWithDocumentAndApostille(int id) {
        Order order = checkNotFoundWithId(orderRepository.getWithDocument(id).orElse(null), id);
        List<Apostille> apostilles = apostilleRepository.getAllByOrderId(id);
        order.setApostilles(apostilles);
        return order;
    }


    public Order create(Order order) {
        checkNew(order);
        checkDateBoundaries(order.getCreationDate(), order.getDeliveryDate());
        return orderRepository.save(order);
    }

    public void update(Order order) {
        checkDateBoundaries(order.getCreationDate(), order.getDeliveryDate());
        updateSurcharge(order);
        orderRepository.save(order);
    }

    public void delete(int id) {
        Order delete = checkNotFoundWithId(orderRepository.getWithDocument(id).orElse(null), id);
        orderRepository.delete(delete);
    }

    public Order getReference(int id) {
        return orderRepository.getReferenceById(id);
    }

    public void recalculateOrderCostAndSave(int id) {
        Order order = getWithDocumentAndApostille(id);
        recalculateOrderCost(order);
        orderRepository.save(order);
    }

    private void recalculateOrderCost(Order order) {
        int orderCost = OrderUtil.calculateOrderCost(order);
        order.setSummaryCost(orderCost);

        updateSurcharge(order);
    }

    private void updateSurcharge(Order order) {
        int surcharge = order.getSummaryCost() - order.getPrepaid();
        order.setSurcharge(surcharge);
    }
}
