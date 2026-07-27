package dev.joint.library_management.service;

import dev.joint.library_management.dto.security.JwtResponseDto;
import dev.joint.library_management.dto.security.LoginRequestDto;
import dev.joint.library_management.dto.security.RegisterRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    void register(RegisterRequestDto request);
    JwtResponseDto login(LoginRequestDto request);
}
