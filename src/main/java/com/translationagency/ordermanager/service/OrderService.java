package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.entity.OrderStatus;
import com.translationagency.ordermanager.repository.OrderRepository;
import com.translationagency.ordermanager.util.OrderUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private ApostilleService apostilleService;

    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    public Order get(int id) {
        Order order = orderRepository.getWithDocument(id).orElseThrow(() -> new RuntimeException("Not found"));
        List<Apostille> apostilles = apostilleService.getAllByOrder(id);
        order.setApostilles(apostilles);
        return order;
    }


    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public void update(Order order) {
        orderRepository.save(order);
    }

    public void delete(int id) {
        Order delete = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        orderRepository.delete(delete);
    }

    public void setStatus(int id, boolean isCompleted) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        OrderStatus newStatus = isCompleted ? OrderStatus.COMPLETED : OrderStatus.IN_WORK;

        order.setOrderStatus(newStatus);
    }

    public Order getReference(int id) {
        return orderRepository.getReferenceById(id);
    }

    public void recalculateOrderCost(int id) {
        Order order = get(id);
        int orderCost = OrderUtil.calculateOrderCost(order);
        order.setSummaryCost(orderCost);

        int surcharge = orderCost - order.getPrepaid();
        order.setSurcharge(surcharge);

        update(order);
    }
}
