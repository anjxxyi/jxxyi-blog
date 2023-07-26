package org.anjxxyi.myarticle.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {

    // 쿠키 추가 => 요청값(이름, 값. 만료기간)
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키 삭제 => 쿠키 이름 활용
        // ~> 실제로 삭제는 불가, 파라미터로 넘어온 쿠키를 빈값으로 바꾸고 만료시간을 0으로 설정하여 재생성시 만료처리
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    // 객체 직렬화 => 쿠키값으로 변환
    public static String serialize(Object o) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(o));
    }

    // 쿠키 역직렬화 => 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> tClass) {
        return tClass.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
