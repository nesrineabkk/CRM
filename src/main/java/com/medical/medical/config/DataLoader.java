package com.medical.medical.config;

import com.medical.medical.entity.Doctor;
import com.medical.medical.enums.Role;
import com.medical.medical.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{

    private final PasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;

    public DataLoader(PasswordEncoder passwordEncoder ,DoctorRepository doctorRepository) {
        this.passwordEncoder = passwordEncoder;
        this.doctorRepository =doctorRepository;
    }

    @Override
    public void run(String... args) {
       /* boolean adminExists = doctorRepository.existsByRole(Role.ROLE_ADMIN);
        if (!adminExists) {
            Doctor admin = new Doctor();
            admin.setEmail("admin@hospital.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setRole(Role.ROLE_ADMIN);
            doctorRepository.save(admin);
        }*/
        }

    }
