package com.medical.medical.controller;

import com.medical.medical.entity.Doctor;
import com.medical.medical.enums.Role;
import com.medical.medical.repository.DoctorRepository;
import com.medical.medical.security.DoctorUserDetails;
import com.medical.medical.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminToken;
    private String doctorToken;

    @BeforeEach
    void setup() {
        doctorRepository.deleteAll();

        Doctor admin = Doctor.builder()
                .firstName("Admin")
                .lastName("User")
                .email("admin@hospital.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.ROLE_ADMIN)
                .specialty("General")
                .phone("123456789")
                .build();

        doctorRepository.save(admin);
        adminToken = jwtService.generateTokenForDoctor(admin);

        Doctor doctor = Doctor.builder()
                .firstName("Doc")
                .lastName("Tor")
                .email("doctor@hospital.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.ROLE_DOCTOR)
                .specialty("General")
                .phone("987654321")
                .build();
        doctorRepository.save(doctor);

        doctorToken = jwtService.generateTokenForDoctor(doctor);
    }


    @Test
    void admin_can_create_doctor() throws Exception {
        String newDoctorJson = """
                    {
                        "firstName": "John",
                                 "lastName": "Doe",
                                 "email": "john@example.com",
                                 "password": "123456",
                                 "specialty": "Cardiology",
                                 "phone": "987654321"
                    }
                """;
        // Perform POST request to create doctor
        mockMvc.perform(
                        post("/api/doctors") // endpoint
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken) // auth header
                                .contentType(MediaType.APPLICATION_JSON) // specify JSON body
                                .content(newDoctorJson) // the body
                ).andDo(print())              // 👈 PUT IT HERE
                .andExpect(status().isOk());
    }

    @Test
    void admin_can_get_all_doctors() throws Exception {
        mockMvc.perform(get("/api/doctors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void admin_can_get_doctor_by_id() throws Exception {
        Doctor doc = doctorRepository.save(
                Doctor.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane@example.com")
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.ROLE_ADMIN)
                        .specialty("Dermatology")
                        .phone("111222333")
                        .build()
        );

        mockMvc.perform(get("/api/doctors/" + doc.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void admin_can_update_doctor() throws Exception {
        Doctor doc = doctorRepository.save(
                Doctor.builder()
                        .firstName("Mike")
                        .lastName("Ross")
                        .email("mike@example.com")
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.ROLE_ADMIN)
                        .specialty("Surgery")
                        .phone("555666777")
                        .build()
        );

        String updateJson = """
                    {
                        "firstName": "John",
                                 "lastName": "Doe",
                                 "email": "john@example.com",
                                 "password": "123456",
                                 "specialty": "Cardiology",
                                 "phone": "987654321"
                    }
                """;

        mockMvc.perform(put("/api/doctors/" + doc.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken) // auth header
                .contentType(MediaType.APPLICATION_JSON) // specify JSON body
                .content(updateJson) // the body
        ).andExpect(status().isOk()); // expect 200 OK
    }

    @Test
    void admin_can_delete_doctor() throws Exception {
        Doctor doc = doctorRepository.save(
                Doctor.builder()
                        .firstName("Alice")
                        .lastName("Cooper")
                        .email("alice@example.com")
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.ROLE_ADMIN)
                        .specialty("Neurology")
                        .phone("999888777")
                        .build()
        );
        mockMvc.perform(delete("/api/doctors/" + doc.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk());
        assert (doctorRepository.findById(doc.getId()).isEmpty());
    }

    @Test
    void access_without_token_should_be_forbidden() throws Exception {
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isForbidden());
    }


    @Test
    void create_doctor_missing_email_should_fail() throws Exception {
        String invalidJson = """
                    {
                      "firstName": "John",
                      "lastName": "Doe",
                      "password": "123456"
                    }
                """;

        mockMvc.perform(post("/api/doctors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_non_existing_doctor_should_return_404() throws Exception {
        mockMvc.perform(get("/api/doctors/9999")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void doctor_cannot_access_admin_endpoint() throws Exception {
        mockMvc.perform(
                get("/api/doctors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + doctorToken)
        ).andExpect(status().isForbidden());
    }
}