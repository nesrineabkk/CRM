package com.medical.medical.controller;


import com.medical.medical.dto.UserRequestDto;
import com.medical.medical.dto.UserResponseDto;
import com.medical.medical.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "User (Patient) management")
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user and assign to a doctor")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @PostMapping("/doctor/{doctorId}")
    public UserResponseDto createUser(
            @RequestBody @Valid UserRequestDto dto,
            @PathVariable Long doctorId) {
        return userService.createUser(dto, doctorId);
    }

    @Operation(summary = "Get All Users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<UserResponseDto> getAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @Operation(summary = "Get User By Id ")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Update User ")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public UserResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDto dto) {
        return userService.updateUser(id, dto);
    }

    @Operation(summary = "Delete User")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Change User Doctor ")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/doctor/{doctorId}")
    public UserResponseDto changeUserDoctor(
            @PathVariable Long userId,
            @PathVariable Long doctorId) {
        return userService.changeDoctor(userId, doctorId);
    }

}
