package org.anjxxyi.myarticle.service;

import org.anjxxyi.myarticle.config.jwt.TokenProvider;
import org.anjxxyi.myarticle.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    /**
     * 로그인 인증이 된 사용자에게 access토큰 발급
     * @return userId
     *
     */
    public String createNewAccessToken(User user) {
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

    /**
     * refresh토큰으로 access토큰을 발급
     * @param refreshToken
     * @return
     */
    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
