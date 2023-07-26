package org.anjxxyi.myarticle.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성 ~> (접근 제어자 : protected)
public class Article {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder // 빌터 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
    @Builder // 빌터 패턴으로 객체 생성
    public Article(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }


    // 글 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /*
    * 컬럼명    |  자료형         |  null허용  |  키      |  설명
    * id       |  bigint        |  N         |  기본키  |  일련번호, 기본키
    * title    |  varchar(255)  |  N         |         |  게시물의 제목
    * content  |  varchar(255)  |  N         |         |  게시물의 내용
    */
}
