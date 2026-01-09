package com.medical.medical.mapper;

import com.medical.medical.dto.DoctorRequestDto;
import com.medical.medical.dto.DoctorResponseDto;
import com.medical.medical.entity.Doctor;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DoctorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    Doctor toEntity(DoctorRequestDto dto);

    DoctorResponseDto toDto(Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntity(@MappingTarget Doctor doctor, DoctorRequestDto dto);
}
