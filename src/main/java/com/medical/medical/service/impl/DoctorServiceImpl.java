package com.medical.medical.service.impl;

import com.medical.medical.dto.DoctorRequestDto;
import com.medical.medical.dto.DoctorResponseDto;
import com.medical.medical.entity.Doctor;
import com.medical.medical.enums.Role;
import com.medical.medical.exception.ResourceNotFoundException;
import com.medical.medical.mapper.DoctorMapper;
import com.medical.medical.repository.DoctorRepository;
import com.medical.medical.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl  implements DoctorService {


    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder  passwordEncoder;

    @Override
    public DoctorResponseDto createDoctor(DoctorRequestDto dto) {
        Doctor doctor = doctorMapper.toEntity(dto);
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));
        doctor.setRole(Role.ROLE_DOCTOR);
        Doctor  doctorSaved = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctorSaved);
    }

    @Override
    public DoctorResponseDto updateDoctor(Long id, DoctorRequestDto dto) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id " + id)
                );
        doctorMapper.updateEntity(doctor,dto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponseDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id " + id)
                );

        return doctorMapper.toDto(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponseDto> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(doctorMapper::toDto);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id " + id)
                );
        doctorRepository.delete(doctor);
    }
}
