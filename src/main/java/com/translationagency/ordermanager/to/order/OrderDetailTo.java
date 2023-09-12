package com.translationagency.ordermanager.to.order;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.entity.OrderStatus;
import com.translationagency.ordermanager.to.document.DocumentTo;

import java.time.LocalDate;
import java.util.List;

public record OrderDetailTo(int id, String formattedId, String customerName, String customerPhone, String customerEmail,
                            int prepaid, Integer surcharge, Integer summaryCost, LocalDate creationDate,
                            LocalDate deliveryDate, OrderStatus orderStatus, String note,
                            List<DocumentTo> documentTos, List<Apostille> apostilles) {
}
