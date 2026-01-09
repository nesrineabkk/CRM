package com.medical.medical.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String email;
    private String phone;
}
