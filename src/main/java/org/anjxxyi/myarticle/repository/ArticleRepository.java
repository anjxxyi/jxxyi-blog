package org.anjxxyi.myarticle.repository;

import org.anjxxyi.myarticle.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// *퍼시스턴트(Persistent)계층 : 모든 DB 관련 로직을 처리 => DB에서 데이터를 가져오는 역할
//  @Repository : 테이블에 접근하여 특정 클래스를 매핑(Mapping)하는 인터페이스(interface)파일로 생성한 구현체
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
