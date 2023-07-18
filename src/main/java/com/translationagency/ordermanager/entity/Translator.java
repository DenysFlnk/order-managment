package com.translationagency.ordermanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @Size(min = 5, max = 40)
    private String name;

    @Column(name = "email")
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    private Language language;

    @Column(name = "translator_rate")
    @NotNull
    @NotBlank
    private String translatorRate;
}
