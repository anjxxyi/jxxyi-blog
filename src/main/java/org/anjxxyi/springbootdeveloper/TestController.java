package org.anjxxyi.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// *프레젠테이션(Presentation)계층 : HTTP로 요청을 받고 비즈니스(Business)계층으로 전송하는 역할
@RestController
public class TestController {
    @Autowired  // 빈 주입 ~> TestService
    TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMEmbers() {
        List<Member> members = testService.getAllMembers();
        return members;

    }
}
