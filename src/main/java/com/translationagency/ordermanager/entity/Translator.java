package com.translationagency.ordermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "translator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Translator extends BaseEntity {

    @Column(name = "name")
    @NotNull
    @NotBlank
    @Size(min = 3, max = 40)
    private String name;

    @Column(name = "email")
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "translator")
    @JsonManagedReference
    @JsonIgnore
    @ToString.Exclude
    private List<LanguageRate> rates;

    @Column(name = "available")
    private boolean available;

    public Translator(Integer id, String name, String email, String phoneNumber, List<LanguageRate> rates,
                      boolean available) {
        super(id);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rates = rates;
        this.available = available;
    }
}
