package dev.joint.library_management.service.impl;

import dev.joint.library_management.dto.security.JwtResponseDto;
import dev.joint.library_management.dto.security.LoginRequestDto;
import dev.joint.library_management.dto.security.RegisterRequestDto;
import dev.joint.library_management.entity.security.Role;
import dev.joint.library_management.entity.security.User;
import dev.joint.library_management.repository.security.RoleRepository;
import dev.joint.library_management.repository.security.UserRepository;
import dev.joint.library_management.service.JwtService;
import dev.joint.library_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequestDto request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRoles(List.of(role));

        userRepository.save(user);
    }

    @Override
    public JwtResponseDto login(LoginRequestDto request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.issueToken(authentication);

        return new JwtResponseDto(token);
    }
}

