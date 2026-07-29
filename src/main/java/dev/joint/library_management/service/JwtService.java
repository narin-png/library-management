package dev.joint.library_management.service;

import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Claims;

public interface JwtService {
    Claims parseToken(String token);
    String issueToken(Authentication authentication);
}
