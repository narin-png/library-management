package dev.joint.library_management.service;

import dev.joint.library_management.models.SignInRequest;
import dev.joint.library_management.models.SignInResponse;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);

    void setCookies(HttpHeaders headers, SignInResponse signInResponse);

    void clearCookie(HttpHeaders headers);

    void signOut(String refreshToken);



    SignInResponse refreshCookie(String refreshToken);

}
