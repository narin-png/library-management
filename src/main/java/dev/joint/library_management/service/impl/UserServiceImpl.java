package dev.joint.library_management.service.impl;

import dev.joint.library_management.dto.security.RegisterRequestDto;
import dev.joint.library_management.entity.security.Role;
import dev.joint.library_management.entity.security.User;
import dev.joint.library_management.repository.security.RoleRepository;
import dev.joint.library_management.repository.security.UserRepository;
import dev.joint.library_management.service.UserService;
import lombok.RequiredArgsConstructor;
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

}

