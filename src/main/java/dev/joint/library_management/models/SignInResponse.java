package dev.joint.library_management.models;

import dev.joint.library_management.dto.security.RefreshTokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInResponse {
    private AccessTokenResponse accessToken;
    private RefreshTokenDto refreshToken;
}
