package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.entity.OrderStatus;
import com.translationagency.ordermanager.service.OrderService;
import com.translationagency.ordermanager.to.OrderDetailTo;
import com.translationagency.ordermanager.to.OrderTo;
import com.translationagency.ordermanager.util.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = OrderController.ORDER_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class OrderController {

    public static final String ORDER_REST_URL = "rest-api/orders";

    public static final String ORDER_URL = "/orders";

    private OrderService orderService;

    @GetMapping
    public List<OrderTo> getAll() {
        log.info("getAll");
        return OrderUtil.getTos(orderService.getAll());
    }

    @GetMapping("/{id}")
    public OrderDetailTo get(@PathVariable int id) {
        log.info("get {}", id);
        return OrderUtil.getDetailTo(orderService.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Order order) {
        log.info("create {}", order);
        Order created = orderService.create(order);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ORDER_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Order order) {
        log.info("update {}", order);
        orderService.update(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        orderService.delete(id);
    }

    @PatchMapping("/{id}")
    public void setStatus(@PathVariable int id, @RequestParam boolean isCompleted) {
        log.info("setStatus {} to {}", isCompleted ? OrderStatus.COMPLETED : OrderStatus.IN_WORK, id);
        orderService.setStatus(id, isCompleted);
    }
}
