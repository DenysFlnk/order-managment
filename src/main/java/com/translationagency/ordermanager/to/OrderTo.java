package com.translationagency.ordermanager.to;

import com.translationagency.ordermanager.entity.OrderStatus;

import java.time.LocalDate;

public record OrderTo(int id, String formattedId, String customerName, String customerPhone, String customerEmail,
                      int prepaid, int surcharge, int summaryCost, LocalDate creationDate,
                      LocalDate deliveryDate, OrderStatus orderStatus, String note) {
}