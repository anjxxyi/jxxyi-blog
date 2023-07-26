package org.anjxxyi.myarticle.service;

import org.anjxxyi.myarticle.model.RefreshToken;
import org.anjxxyi.myarticle.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new IllegalArgumentException("Unexpected Token"));
    }
}
