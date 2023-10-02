package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

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

    public Document(Document document) {
        super(document.getId());
        this.order = document.getOrder();
        this.documentLanguage = document.getDocumentLanguage();
        this.isHardComplexity = document.getIsHardComplexity();
        this.officeRate = document.getOfficeRate();
        this.signsNumber = document.getSignsNumber();
        this.notarizationCost = document.getNotarizationCost();
        this.officeCost = document.getOfficeCost();
        this.translator = document.getTranslator();
        this.translatorRate = document.getTranslatorRate();
        this.translatorTax = document.getTranslatorTax();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Document document = (Document) o;
        return officeRate == document.officeRate && notarizationCost == document.notarizationCost &&
                officeCost == document.officeCost && documentLanguage == document.documentLanguage &&
                Objects.equals(isHardComplexity, document.isHardComplexity) &&
                Objects.equals(signsNumber, document.signsNumber) && Objects.equals(translator, document.translator) &&
                Objects.equals(translatorRate, document.translatorRate) &&
                Objects.equals(translatorTax, document.translatorTax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), documentLanguage, isHardComplexity, officeRate, signsNumber,
                notarizationCost, officeCost, translator, translatorRate, translatorTax);
    }
}
