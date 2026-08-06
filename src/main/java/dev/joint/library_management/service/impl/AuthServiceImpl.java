package dev.joint.library_management.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joint.library_management.config.JwtFilter;
import dev.joint.library_management.dto.security.RefreshTokenDto;
import dev.joint.library_management.entity.security.RefreshToken;
import dev.joint.library_management.models.AccessTokenResponse;
import dev.joint.library_management.models.SignInRequest;
import dev.joint.library_management.models.SignInResponse;
import dev.joint.library_management.repository.security.RefreshTokenRepository;
import dev.joint.library_management.service.AuthService;
import dev.joint.library_management.service.JwtService;
import dev.joint.library_management.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${spring.security.jwt.access-expire-time}")
    private Integer accessExpireTime;
    @Value("${spring.security.jwt.refresh-expire-time}")
    private Integer refreshExpireTime;
    private final ObjectMapper objectMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    private final UserService userService;
    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = jwtService.issueToken(authenticate);
        AccessTokenResponse accessTokenResponse =new AccessTokenResponse(token);
        RefreshTokenDto refreshTokenDTO=issueRefreshToken(signInRequest.getUsername());
        return new SignInResponse(accessTokenResponse,refreshTokenDTO);
    }

    private RefreshTokenDto issueRefreshToken(String username) {
        RefreshToken refreshToken=new RefreshToken(UUID.randomUUID().toString(),username,true,new Date(),new Date(System.currentTimeMillis()+refreshExpireTime));
        refreshTokenRepository.save(refreshToken);
        return objectMapper.convertValue(refreshToken,RefreshTokenDto.class);
    }
    @Override
    public void setCookies(HttpHeaders httpHeaders, SignInResponse signInResponse){
        ResponseCookie accessCookie=ResponseCookie.from(JwtFilter.ACCESS_TOKEN,signInResponse.getAccessToken().getToken())
                .maxAge(accessExpireTime)
                .path("/")
                .secure(cookieSecure)
                .httpOnly(true)
                .sameSite("LAX") //LAX, STRICT , NONE
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        ResponseCookie refreshCookie=ResponseCookie.from(JwtFilter.REFRESH_TOKEN,signInResponse.getRefreshToken().getToken())
                .maxAge(accessExpireTime)
                .path("/")
                .secure(cookieSecure)
                .httpOnly(true)
                .sameSite("LAX") //LAX, STRICT , NONE
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
    @Override
    public  void clearCookie(HttpHeaders headers) {
        ResponseCookie accessCookie=ResponseCookie.from(JwtFilter.ACCESS_TOKEN,"")
                .maxAge(0)
                .path("/")
                .secure(cookieSecure)
                .httpOnly(true)
                .sameSite("LAX") //LAX, STRICT , NONE
                .build();
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        ResponseCookie refreshCookie=ResponseCookie.from(JwtFilter.REFRESH_TOKEN,"")
                .maxAge(0)
                .path("/")
                .secure(cookieSecure)
                .httpOnly(true)
                .sameSite("LAX") //LAX, STRICT , NONE
                .build();
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    @Override
    public void signOut(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).
                ifPresent(refresh -> {
                    refresh.setValid(false);
                    refreshTokenRepository.save(refresh);
                });
    }

    @Override
    public SignInResponse refreshCookie(String refreshToken) {


        RefreshToken refreshTokenDb = refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                () -> new BadCredentialsException("Refresh not found"));
        if (!refreshTokenDb.getValid())
            throw new BadCredentialsException("Invalid refresh token");
        if (refreshTokenDb.getExpiresDate().before(new Date()))
            throw new BadCredentialsException("Expired refresh token");
        UserDetails userDetails = userService.loadUserByUsername(refreshTokenDb.getUserName());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse(jwtService.issueToken(usernamePasswordAuthenticationToken));
        refreshTokenDb.setValid(false);
        refreshTokenRepository.save(refreshTokenDb);
        RefreshTokenDto refreshTokenDto = issueRefreshToken(refreshTokenDb.getUserName());
        return new SignInResponse(accessTokenResponse, refreshTokenDto);
    }

}
