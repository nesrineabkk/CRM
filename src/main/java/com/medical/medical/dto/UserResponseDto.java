package com.medical.medical.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String country;
    private String nationality;
    private String phone;
    private LocalDate birthDate;
    private String placeOfBirth;
    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;

}
