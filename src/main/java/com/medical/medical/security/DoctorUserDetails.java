package com.medical.medical.security;

import com.medical.medical.entity.Doctor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class DoctorUserDetails  implements UserDetails {
    private final Doctor doctor;

    public DoctorUserDetails(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String getUsername() {
        return doctor.getEmail();
    }

    @Override
    public String getPassword() {
        return doctor.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(doctor.getRole().name()));
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
