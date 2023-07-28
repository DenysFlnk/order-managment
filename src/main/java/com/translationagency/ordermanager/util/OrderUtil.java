package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.to.OrderDetailTo;
import com.translationagency.ordermanager.to.OrderTo;

import java.util.List;

public class OrderUtil {

    public static final String ORDER_REST_URL = "rest-api/orders";

    public static final String ID_PREFIX = "KP0";

    private OrderUtil(){}

    public static List<OrderDetailTo> getDetailTos(List<Order> orders) {
        return orders.stream().map(OrderUtil::getDetailTo).toList();
    }

    public static OrderDetailTo getDetailTo(Order order) {
        return new OrderDetailTo(order.id(), getFormattedId(order.id()), order.getCustomerName(),
                order.getCustomerPhone(), order.getCustomerEmail(), order.getPrepaid(), order.getSurcharge(),
                order.getSummaryCost(), order.getCreationDate(), order.getDeliveryDate(),
                order.getOrderStatus(), order.getNote(), DocumentUtil.getTos(order.getDocuments()),
                order.getApostilles());
    }

    public static List<OrderTo> getTos(List<Order> orders) {
        return orders.stream().map(OrderUtil::getTo).toList();
    }

    private static OrderTo getTo(Order order) {
        return new OrderTo(order.id(), getFormattedId(order.id()), order.getCustomerName(),
                order.getCustomerPhone(), order.getCustomerEmail(), order.getPrepaid(), order.getSurcharge(),
                order.getSummaryCost(), order.getCreationDate(), order.getDeliveryDate(),
                order.getOrderStatus(), order.getNote());
    }

    private static String getFormattedId(int id) {
        return ID_PREFIX + id;
    }
}
