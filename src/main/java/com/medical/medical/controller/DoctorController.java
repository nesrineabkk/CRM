package com.medical.medical.controller;

import com.medical.medical.dto.DoctorRequestDto;
import com.medical.medical.dto.DoctorResponseDto;
import com.medical.medical.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Doctors", description = "Doctor management")
@RestController
@RequestMapping("/api/doctors")
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService service) {
        this.doctorService = service;
    }

    @Operation(summary = "Create a new  Doctor ")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DoctorResponseDto create(
            @RequestBody @Valid DoctorRequestDto dto) {
        return doctorService.createDoctor(dto);

    }

    @Operation(summary = "List of Doctors ")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<DoctorResponseDto> getAll(Pageable pageable) {
        System.out.println("INSIDE CONTROLLER");
        return doctorService.getAllDoctors(pageable);
    }

    @Operation(summary = "Get Doctor By Id ")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public DoctorResponseDto getById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @Operation(summary = "Update Doctor ")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public DoctorResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid DoctorRequestDto dto) {
        return doctorService.updateDoctor(id, dto);
    }

    @Operation(summary = "Delete Doctor")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
