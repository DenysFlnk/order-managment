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
import java.util.Objects;

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
    private Integer surcharge;

    @Column(name = "summary_cost")
    private Integer summaryCost;

    @Column(name = "creation_date")
    @NotNull
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference
    @ToString.Exclude
    private List<Document> documents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference
    @ToString.Exclude
    private List<Apostille> apostilles;

    public Order(Integer id, String customerName, String customerPhone, String customerEmail, int prepaid,
                 Integer surcharge, Integer summaryCost, LocalDate creationDate, LocalDate deliveryDate,
                 OrderStatus orderStatus, String note, List<Document> documents, List<Apostille> apostilles) {
        super(id);
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.prepaid = prepaid;
        this.surcharge = surcharge;
        this.summaryCost = summaryCost;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.orderStatus = orderStatus;
        this.note = note;
        this.documents = documents;
        this.apostilles = apostilles;
    }

    public Order(Order order) {
        super(order.getId());
        this.customerName = order.getCustomerName();
        this.customerPhone = order.getCustomerPhone();
        this.customerEmail = order.getCustomerEmail();
        this.prepaid = order.getPrepaid();
        this.surcharge = order.getSurcharge();
        this.summaryCost = order.getSummaryCost();
        this.creationDate = order.getCreationDate();
        this.deliveryDate = order.getDeliveryDate();
        this.orderStatus = order.getOrderStatus();
        this.note = order.getNote();
        this.documents = order.getDocuments();
        this.apostilles = order.getApostilles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return prepaid == order.prepaid && customerName.equals(order.customerName) &&
                Objects.equals(customerPhone, order.customerPhone) &&
                Objects.equals(customerEmail, order.customerEmail) && Objects.equals(surcharge, order.surcharge) &&
                Objects.equals(summaryCost, order.summaryCost) && creationDate.equals(order.creationDate) &&
                deliveryDate.equals(order.deliveryDate) && orderStatus == order.orderStatus &&
                Objects.equals(note, order.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerName, customerPhone, customerEmail, prepaid, surcharge,
                summaryCost, creationDate, deliveryDate, orderStatus, note);
    }
}
