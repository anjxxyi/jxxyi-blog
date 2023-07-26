package org.anjxxyi.myarticle.config.jwt;

import io.jsonwebtoken.Jwts;
import org.anjxxyi.myarticle.model.User;
import org.anjxxyi.myarticle.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("generateToken() : 유저정보와 만료기간을 전달하여 토큰생성")
    @Test
    void generateTokenTest() {
        // given => 테스트 유저 데이터 생성
        User testUser = userRepository.save(User.builder()
                .email("user1@abc.com")
                .password("abcd")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        System.out.println("-----------genrateToken: " + token);
        token += "a";

        // then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validJwtToken() : 만료된 토큰일 경우 유효성 검증 실패")
    @Test
    void validJwtTokenTest() {
        // given => 테스트 유저 데이터 생성
        User testUser = userRepository.save(User.builder()
                .email("user1@abc.com")
                .password("abcd")
                .build());
        Date now = new Date();
        String token = JwtFactory.builder()
                .expiration(new Date(now.getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }
}