package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @ToString.Exclude
    private int surcharge;

    @Column(name = "summary_cost")
    @ToString.Exclude
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
    @ToString.Exclude
    private String note;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference
    @ToString.Exclude
    private List<Document> documents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference
    @ToString.Exclude
    private List<Apostille> apostilles;
}
