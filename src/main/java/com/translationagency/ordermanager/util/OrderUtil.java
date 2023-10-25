package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.to.order.OrderDetailTo;
import com.translationagency.ordermanager.to.order.OrderTo;

import java.util.List;

public class OrderUtil {

    public static final String ID_PREFIX = "KP0";

    private OrderUtil(){}

    public static int calculateOrderCost(Order order){
        int documentsCost = order.getDocuments().stream().mapToInt(Document::getOfficeCost).sum();
        int apostillesCost = order.getApostilles().stream().mapToInt(Apostille::getCost).sum();

        return documentsCost + apostillesCost;
    }

    public static OrderDetailTo getDetailTo(Order order) {
        return new OrderDetailTo(order.id(), getFormattedId(order.id()), order.getCustomerName(),
                order.getCustomerContact(), order.getPrepaid(), order.getSurcharge(),
                order.getSummaryCost(), order.getCreationDate(), order.getDeliveryDate(),
                order.getOrderStatus(), order.getNote(), DocumentUtil.getTos(order.getDocuments()),
                order.getApostilles());
    }

    public static List<OrderTo> getTos(List<Order> orders) {
        return orders.stream().map(OrderUtil::getTo).toList();
    }

    private static OrderTo getTo(Order order) {
        return new OrderTo(order.id(), DocumentUtil.notarizationCostSum(order.getDocuments()),
                getFormattedId(order.id()), order.getCustomerName(),
                order.getCustomerContact(), order.getPrepaid(), order.getSurcharge(),
                order.getSummaryCost(), order.getCreationDate(), order.getDeliveryDate(),
                order.getOrderStatus(), order.getNote());
    }

    private static String getFormattedId(int id) {
        return ID_PREFIX + id;
    }
}
