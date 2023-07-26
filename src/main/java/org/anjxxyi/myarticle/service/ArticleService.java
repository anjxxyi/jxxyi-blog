package org.anjxxyi.myarticle.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anjxxyi.myarticle.model.Article;
import org.anjxxyi.myarticle.model.dto.AddArticleRequest;
import org.anjxxyi.myarticle.model.dto.UpdateArticleRequest;
import org.anjxxyi.myarticle.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 빈으로 등록
@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 추가
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 새 글 저장
    public Article save(AddArticleRequest addArticleRequest, String author) {
        return articleRepository.save(addArticleRequest.toEntity(author));
    }

    // 글 목록
    public List<Article> getArticleAll() {
        return articleRepository.findAll();
    }

    // 글 상세정보
    public Article getArticle(long articleId) {
        return articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("not found : " + articleId)
        );
    }

    // 글 삭제
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    // 글 수정
    @Transactional // 트랜잭션 메서드 : DB에서 데이터를 바꾸기 위한 작업 단위 (ex: 계좌이체(AtoB) => A계좌 출금 후, B계좌 입금)
    public Article update(long articleId, UpdateArticleRequest updateArticleRequest) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("not found : " + articleId));

        // @Transactional 필요없는 코드
//        article.setTitle(updateArticleRequest.getTitle());
//        article.setContent(updateArticleRequest.getContent());
//        return articleRepository.save(article);    // update

        article.update(updateArticleRequest.getTitle(), updateArticleRequest.getContent());
        return article;
    }
}
