package com.medical.medical.repository;

import com.medical.medical.entity.Doctor;
import com.medical.medical.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository  extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    boolean existsByRole(Role role);
}
