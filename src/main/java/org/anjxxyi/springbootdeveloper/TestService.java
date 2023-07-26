package org.anjxxyi.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// *비즈니스(Business)계층 : 모든 비즈니스 로직을 처리하는 역할
//  비즈니스 로직이란? 웹 사이트에서 벌어지는 모든 작업의 프로세서를 구현하기 위한 로직
@Service
public class TestService {
    @Autowired  // 빈 주입 ~> MemberRepository
    MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // 모든 멤버 목록 출력
    }
}
