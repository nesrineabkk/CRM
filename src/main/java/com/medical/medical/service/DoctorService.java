package com.medical.medical.service;

import com.medical.medical.dto.DoctorRequestDto;
import com.medical.medical.dto.DoctorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DoctorService {
    DoctorResponseDto createDoctor(DoctorRequestDto dto);
    DoctorResponseDto updateDoctor(Long id, DoctorRequestDto dto);
    DoctorResponseDto getDoctorById(Long id);
    Page<DoctorResponseDto> getAllDoctors(Pageable pageable);
    void deleteDoctor(Long id);

}
