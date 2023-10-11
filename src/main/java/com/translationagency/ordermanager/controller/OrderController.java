package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.service.OrderService;
import com.translationagency.ordermanager.to.order.OrderDetailTo;
import com.translationagency.ordermanager.to.order.OrderTo;
import com.translationagency.ordermanager.util.OrderUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = OrderController.ORDER_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class OrderController {

    public static final String ORDER_REST_URL = "rest-api/orders";

    public static final String ORDER_URL = "/orders";

    private OrderService orderService;

    @GetMapping
    public List<OrderTo> getAll(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("getAll");
        Pageable pageable = PageRequest.of(page, size);
        return OrderUtil.getTos(orderService.getAll(pageable));
    }

    @GetMapping("/count")
    public int getAllCount() {
        log.info("getAllCount");
        return orderService.getAllCount();
    }

    @GetMapping("/{id}")
    public OrderDetailTo get(@PathVariable int id) {
        log.info("get {}", id);
        return OrderUtil.getDetailTo(orderService.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@Valid @RequestBody Order order) {
        log.info("create {}", order);
        Order created = orderService.create(order);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ORDER_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).build();
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Order order, @PathVariable int id) {
        log.info("update {}", order);
        assureIdConsistent(order, id);
        orderService.update(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        orderService.delete(id);
    }
}
