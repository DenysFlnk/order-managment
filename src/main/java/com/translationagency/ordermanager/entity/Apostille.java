package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "apostille")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apostille extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Order order;

    @Column(name = "title")
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String title;

    @Column(name = "submission_country")
    @NotNull
    @NotBlank
    @Size(min = 2)
    private String submissionCountry;

    @Column(name = "submission_department")
    @NotNull
    @NotBlank
    private String submissionDepartment;

    @Column(name = "apostille_cost")
    @NotNull
    @Range(min = 1)
    private int cost;

    public Apostille(Integer id, Order order, String title, String submissionCountry, String submissionDepartment,
                     int cost) {
        super(id);
        this.order = order;
        this.title = title;
        this.submissionCountry = submissionCountry;
        this.submissionDepartment = submissionDepartment;
        this.cost = cost;
    }

    public Apostille(Apostille apostille) {
        super(apostille.id);
        this.order = apostille.getOrder();
        this.title = apostille.getTitle();
        this.submissionCountry = apostille.getSubmissionCountry();
        this.submissionDepartment = apostille.getSubmissionDepartment();
        this.cost = apostille.getCost();
    }
}
