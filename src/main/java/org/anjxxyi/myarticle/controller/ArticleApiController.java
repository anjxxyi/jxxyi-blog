package org.anjxxyi.myarticle.controller;

import lombok.RequiredArgsConstructor;
import org.anjxxyi.myarticle.model.Article;
import org.anjxxyi.myarticle.model.dto.AddArticleRequest;
import org.anjxxyi.myarticle.model.dto.ArticleResponse;
import org.anjxxyi.myarticle.model.dto.UpdateArticleRequest;
import org.anjxxyi.myarticle.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController           // HTTP Reponse Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequiredArgsConstructor  // final이 붙거나 @NotNull이 붙은 필드의 생성자를 추가
public class ArticleApiController {
    private final ArticleService articleService;

    // POST => 새 글 추가
    @PostMapping("/api/articles") // HTTP 메서드가 POST일 때 전달받은 URL과 일치하면 메서드로 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, String author) { // 요청 본문 값 매핑
        Article savedArticle = articleService.save(request, author);   // 글 저장

        // 요청한 자원이 성공적으로 생성되었으며, 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    // GET => 글 목록
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<Article> articleList = articleService.getArticleAll();

        // stream을 이용한 방식
//        List<ArticleResponse> articleResponseList = articleList.stream()
//                .map(ArticleResponse::new)
//                .toList();
        // 옛날에 쓰던 방식
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleResponse articleResponse = new ArticleResponse(article);
            articleResponseList.add(articleResponse);
        }
        // Id 내림차순으로 변경
        Collections.sort(articleResponseList, Comparator.comparingLong(ArticleResponse::getId).reversed());

//        return ResponseEntity.status(HttpStatus.OK)
        return ResponseEntity.ok().body(articleResponseList);
    }

    // GET => 글 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = articleService.getArticle(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // DELETE => 글 삭제
    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
    public ResponseEntity<String> deleteArticle(@PathVariable long id) {
        articleService.delete(id);

        return ResponseEntity.ok()
                .body(new String("삭세한 블로그 글 번호id: " + id));
    }

    // PUT => 글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = articleService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }
}
