package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "language_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LanguageRate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Translator translator;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Language language;

    @Column(name = "common_rate")
    @NotNull
    @Range(min = 1)
    private int commonRate;

    @Column(name = "hard_rate")
    @NotNull
    @Range(min = 1)
    private int hardRate;

    @Column(name = "signs")
    @NotNull
    @Range(min = 1)
    private double signs;

    public LanguageRate(Integer id, Translator translator, Language language, int commonRate, int hardRate,
                        double signs) {
        super(id);
        this.translator = translator;
        this.language = language;
        this.commonRate = commonRate;
        this.hardRate = hardRate;
        this.signs = signs;
    }
}
