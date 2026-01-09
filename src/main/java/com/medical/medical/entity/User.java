package com.medical.medical.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Users_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private LocalDate birthDate;

    private String placeOfBirth;

    private String address;
    private String email;
    private String phone;

    private String city;
    private String country;
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
