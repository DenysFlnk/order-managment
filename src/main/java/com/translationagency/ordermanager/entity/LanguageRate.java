package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LanguageRate that = (LanguageRate) o;
        return commonRate == that.commonRate && hardRate == that.hardRate && Double.compare(that.signs, signs) == 0 &&
                language == that.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), language, commonRate, hardRate, signs);
    }
}
