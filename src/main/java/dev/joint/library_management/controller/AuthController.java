package dev.joint.library_management.controller;

import dev.joint.library_management.config.JwtFilter;
import dev.joint.library_management.dto.security.JwtResponseDto;
import dev.joint.library_management.dto.security.LoginRequestDto;
import dev.joint.library_management.dto.security.RegisterRequestDto;
import dev.joint.library_management.models.SignInRequest;
import dev.joint.library_management.models.SignInResponse;
import dev.joint.library_management.service.AuthService;
import dev.joint.library_management.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Security management APIs")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> token(@RequestBody SignInRequest signInRequest){
        SignInResponse signInResponse = authService.signIn(signInRequest);
        authService.signIn( signInRequest );
        HttpHeaders headers = new HttpHeaders();
        authService.setCookies(headers, signInResponse);
        return new ResponseEntity<>(signInResponse,headers, HttpStatus.OK);
    }


    @PostMapping("/sign-out")
    public ResponseEntity<?> signout(@CookieValue(name= JwtFilter.REFRESH_TOKEN) String refreshToken){
        authService.signOut(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        authService.clearCookie(headers);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh-token") String refreshToken) {
        SignInResponse signInResponse = authService.refreshCookie(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        authService.setCookies(headers, signInResponse);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

    }
}
