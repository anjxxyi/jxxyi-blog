package org.anjxxyi.myarticle.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.anjxxyi.myarticle.model.User;
import org.anjxxyi.myarticle.model.dto.AddUserRequest;
import org.anjxxyi.myarticle.model.dto.LoginRequest;
import org.anjxxyi.myarticle.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.register(request);  // 회원가입 메서드 호출
        return  "redirect:/login";  // 회원가입 완료 후 로그인 페이지로 이동
    }

    @PostMapping("/login")
    public String login(LoginRequest request) {
//        System.out.println("로그인 서비스 동작");
        User user = userService.login(request);

        return "redirect:/user?" + user.getAccessToken();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect::login";
    }

    @GetMapping("/oauth2logout")
    public String oauth2logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/oauth2login";
    }
}
