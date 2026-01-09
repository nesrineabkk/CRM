package com.medical.medical.service;

import com.medical.medical.dto.UserRequestDto;
import com.medical.medical.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserService {

    UserResponseDto createUser(UserRequestDto dto, Long doctorId);

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    UserResponseDto changeDoctor(Long userId, Long doctorId);

    UserResponseDto getUserById(Long id);

    Page<UserResponseDto> getAllUsers(Pageable pageable);

    void deleteUser(Long id);
}
