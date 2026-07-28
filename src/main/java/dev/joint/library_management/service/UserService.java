package dev.joint.library_management.service;

import dev.joint.library_management.dto.security.JwtResponseDto;
import dev.joint.library_management.dto.security.LoginRequestDto;
import dev.joint.library_management.dto.security.RegisterRequestDto;
import dev.joint.library_management.dto.security.UserDto;
import dev.joint.library_management.models.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService  {
    List<UserDto> getUsers();

    UserDto getUserById(Integer id);

    UserDto createUser(UserDto userDto);

    void deleteUser(Integer id);

    UserDto updateUser(Integer id, UserDto userDto);
    UserRequest updateUser(Integer id, UserRequest userRequest);
    void register(RegisterRequestDto request);
}
