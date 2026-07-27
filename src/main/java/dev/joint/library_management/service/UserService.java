package dev.joint.library_management.service;

import dev.joint.library_management.dto.security.RegisterRequestDto;

public interface UserService {
    void register(RegisterRequestDto request);
}
