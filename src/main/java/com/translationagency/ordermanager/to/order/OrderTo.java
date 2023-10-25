package com.translationagency.ordermanager.to.order;

import com.translationagency.ordermanager.entity.OrderStatus;

import java.time.LocalDate;

public record OrderTo(int id, Integer notarizationCost, String formattedId, String customerName, String customerContact,
                      int prepaid, Integer surcharge, Integer summaryCost, LocalDate creationDate,
                      LocalDate deliveryDate, OrderStatus orderStatus, String note) {
}
