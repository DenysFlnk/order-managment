package com.translationagency.ordermanager.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    IN_WORK ("IN WORK"),
    COMPLETED("COMPLETED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
