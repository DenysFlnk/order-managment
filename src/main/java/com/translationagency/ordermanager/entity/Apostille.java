package com.translationagency.ordermanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "apostille")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apostille extends BaseEntity {

    @Column(name = "order_id")
    @NotNull
    private int orderId;

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
}
