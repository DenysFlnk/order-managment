package com.translationagency.ordermanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"surcharge", "summaryCost", "note"})
public class Order extends BaseEntity {

    @Column(name = "customer_name")
    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_email")
    @Email
    private String customerEmail;

    @Column(name = "prepaid")
    @NotNull
    @Range(min = 1)
    private int prepaid;

    @Column(name = "surcharge")
    private int surcharge;

    @Column(name = "summary_cost")
    private int summaryCost;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "delivery_date")
    @NotNull
    private LocalDate deliveryDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "note")
    @Size(max = 200)
    private String note;
}
