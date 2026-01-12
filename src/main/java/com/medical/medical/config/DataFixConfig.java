package com.medical.medical.config;

import com.medical.medical.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataFixConfig {
 /*   @Bean
    CommandLineRunner fixDoctorPasswords(
            DoctorRepository doctorRepository,
            PasswordEncoder passwordEncoder) {

        return args -> doctorRepository.findAll().forEach(doctor -> {
            if (doctor.getPassword() == null) {
                doctor.setPassword(
                        passwordEncoder.encode("123456")
                );
                doctorRepository.save(doctor);
            }
        });
    }*/
}
