package dev.joint.library_management.config;

import dev.joint.library_management.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    public static final String ACCESS_TOKEN = "access-token";
    public static final String REFRESH_TOKEN = "refresh-token";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Optional<String> token=Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
//        if(token.isPresent())
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(ACCESS_TOKEN)).findFirst().ifPresent(cookie ->
            {
                Claims claims = jwtService.parseToken(cookie.getValue());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(claims.getSubject(), null, getAuthorities(claims));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }
        filterChain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        Object raw = claims.get("authorities");
        return ((List<?>) raw).stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
