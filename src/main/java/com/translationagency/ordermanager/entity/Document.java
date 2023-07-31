package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Order order;

    @Column(name = "document_language")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Language documentLanguage;

    @Column(name = "hard_complexity")
    private boolean isHardComplexity;

    @Column(name = "office_rate")
    @NotNull
    @Range(min = 1)
    private int officeRate;

    @Column(name = "signs_number")
    private Double signsNumber;

    @Column(name = "notarization")
    private int notarizationCost;

    @Column(name = "office_cost")
    @NotNull
    private int officeCost;

    @ManyToOne(fetch = FetchType.EAGER)
    private Translator translator;

    @Column(name = "translator_rate")
    @NotNull
    @NotBlank
    private String translatorRate;

    @Column(name = "translator_tax")
    private Integer translatorTax;
}
