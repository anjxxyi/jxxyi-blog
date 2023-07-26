package org.anjxxyi.myarticle.config;

import org.anjxxyi.myarticle.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final String HEADER_AUTHORIZATION = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤드에서 Authorization 키 값 조회
        String authValueInHeader = request.getHeader(HEADER_AUTHORIZATION);
        // Bearer를 없애서 순수 token값만 가져오기
        String token = getAccessToken(authValueInHeader);

        // 가져온 토큰이 유효한지 체크하고 유효하면 SecurityContextHolder에 인증 정보를 저장
        if (!Objects.isNull(token) && tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String headerAuthToken) {
        if (headerAuthToken != null && headerAuthToken.startsWith(TOKEN_PREFIX)) {
            return headerAuthToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
