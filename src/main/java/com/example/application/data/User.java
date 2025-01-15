package com.example.application.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javaparser.ast.Node;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Email
    @NotNull
    private String email;
    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    private String guardianFirstName;
    private String guardianLastName;

    @Pattern(regexp = "\\d{9}", message = "Numer telefonu powinien składać się z 9 cyfr")
    private String phone;

    private String childFirstName;
    private String childLastName;
    private LocalDate birthDate;

}
