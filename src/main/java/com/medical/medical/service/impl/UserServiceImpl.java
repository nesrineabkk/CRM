package com.medical.medical.service.impl;

import com.medical.medical.dto.UserRequestDto;
import com.medical.medical.dto.UserResponseDto;
import com.medical.medical.entity.Doctor;
import com.medical.medical.entity.User;
import com.medical.medical.exception.ResourceNotFoundException;
import com.medical.medical.mapper.UserMapper;
import com.medical.medical.repository.DoctorRepository;
import com.medical.medical.repository.UserRepository;
import com.medical.medical.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto dto, Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id " + doctorId)
                );
        User user = userMapper.toEntity(dto);
        user.setDoctor(doctor);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id)
                );

        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id)
                );

        userMapper.updateEntity(user, dto);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id)
                );

        userRepository.delete(user);
    }

    // CHANGE DOCTOR
    @Override
    public UserResponseDto changeDoctor(Long userId, Long doctorId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id " + doctorId)
                );

        user.setDoctor(doctor);

        return userMapper.toDto(userRepository.save(user));
    }
}
