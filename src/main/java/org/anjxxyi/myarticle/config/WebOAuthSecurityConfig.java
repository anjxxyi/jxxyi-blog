package org.anjxxyi.myarticle.config;

import lombok.RequiredArgsConstructor;
import org.anjxxyi.myarticle.config.jwt.TokenProvider;
import org.anjxxyi.myarticle.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import org.anjxxyi.myarticle.config.oauth.OAuth2SuccessHandler;
import org.anjxxyi.myarticle.config.oauth.OAuth2UserCustomService;
import org.anjxxyi.myarticle.repository.RefreshTokenRepository;
import org.anjxxyi.myarticle.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean // 스프링 시큐리티 기능 비활성화
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 토큰 방식으로 인증을 위해서 폼로그인과 세션방식 비활성화
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 헤더 확인을 위해 커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 토큰 발급 혹은 재발급 URL은 인증 없이 접근이 가능하도록 설정
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/token").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll());

        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/login")
                // Authorization요청과 관련된 상태 저장
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                        .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                // 인증 성공시 실행할 핸들러
                .successHandler(oAuth2SuccessHandler())
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(oAuth2UserCustomService)));

        // 로그아웃 성공시 이동할 URL
        http.logout(logout -> logout
                .logoutSuccessUrl("/login"));

        // /api로 시작하는 url인 경우 401상태 코드 처리(인증이 없다라고 안내)
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**")));

        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
