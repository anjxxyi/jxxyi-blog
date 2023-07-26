package org.anjxxyi.myarticle.service;

import lombok.RequiredArgsConstructor;
import org.anjxxyi.myarticle.model.User;
import org.anjxxyi.myarticle.model.dto.AddUserRequest;
import org.anjxxyi.myarticle.model.dto.LoginRequest;
import org.anjxxyi.myarticle.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final TokenProvider tokenProvider;

    // 회원가입
    public Long register(AddUserRequest dto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return userRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
//                        .password(dto.getPassword())
                        .build()).getId();
    }

    public User login(LoginRequest loginRequest) {
        // email에 일치하는 user의 모든 정보를 가져온다
        Optional<User> optUser = userRepository.findByEmail(loginRequest.getUsername());
        User user = null;
//        if (optUser.isEmpty()) return null;

        /* user = optUser.get();
        // user가 있는지 여부 체크
        if (!Objects.isNull(user)) {
            // 0. 패스워드가 맞는지 비교
            //   (1) 맞으면 1-2-3-4 실행
            //   (2) 틀리면 user값을 null로 세팅
            String encPwd = bCryptPasswordEncoder.encode(loginRequest.getPassword());
//            if (encPwd.equals(user.getPassword())) {
            if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // 1. access토큰을 발급(tokenService.createNewAccessToken(user) 코드 사용)
                String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
                // 2. access토큰을 user java객체에 저장
                user.setAccessToken(accessToken);
                // 3. access토큰을 user db정보에 저장
                userRepository.save(user);
            }
            // 4.(option) 해당 유저에 대한 role저장
        } */
        return user;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
