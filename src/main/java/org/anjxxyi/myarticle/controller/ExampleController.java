package org.anjxxyi.myarticle.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller // 컨트롤러라고 명시적으로 표시
public class ExampleController {

    // View로 데이터를 넘겨주는 모델 객체
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", examplePerson); // Person 객체 지정
        model.addAttribute("today", LocalDate.now());

        return "example"; // example.html 라는 뷰 조회
    }

    @Data
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }

    /*
    * 1) Controller에서 Data "설정"
    * 2) Model객체를 통하여 View로 Data "전달"
         => Model은 Controller와 View의 중간다리 역할
    * */
}
