package org.anjxxyi.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// *퍼시스턴트(Persistent)계층 : 모든 DB 관련 로직을 처리 => DB에서 데이터를 가져오는 역할
//  ~> DB 접근 시 사용할 DAO 생성 후, 실제 DB에 접근하는 코드 작성
//  DAO란? 데이터베이스(Database)계층과 상호작용하기 위한 객체
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
