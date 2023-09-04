package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Order order;

    @Column(name = "document_language")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Language documentLanguage;

    @Column(name = "hard_complexity")
    private Boolean isHardComplexity;

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
    private String translatorRate;

    @Column(name = "translator_tax")
    private Integer translatorTax;

    public Document(Integer id, Order order, Language documentLanguage, Boolean isHardComplexity, int officeRate,
                    Double signsNumber, int notarizationCost, int officeCost, Translator translator,
                    String translatorRate, Integer translatorTax) {
        super(id);
        this.order = order;
        this.documentLanguage = documentLanguage;
        this.isHardComplexity = isHardComplexity;
        this.officeRate = officeRate;
        this.signsNumber = signsNumber;
        this.notarizationCost = notarizationCost;
        this.officeCost = officeCost;
        this.translator = translator;
        this.translatorRate = translatorRate;
        this.translatorTax = translatorTax;
    }
}
