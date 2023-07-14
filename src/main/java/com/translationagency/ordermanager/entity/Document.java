package com.translationagency.ordermanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Document extends BaseEntity {

    @Column(name = "order_id")
    @NotNull
    private int orderId;

    @Column(name = "document_language")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Language documentLanguage;

    @Column(name = "office_rate")
    @NotNull
    @Range(min = 1)
    private int officeRate;

    @Column(name = "signs_number")
    private int signsNumber;

    @Column(name = "notarization")
    private int notarizationCost;

    @Column(name = "office_cost")
    private int officeCost;

    @Column(name = "translator")
    private String translator;

    @Column(name = "translator_rate")
    private String translatorRate;

    @Column(name = "translator_tax")
    private int translatorTax;
}
