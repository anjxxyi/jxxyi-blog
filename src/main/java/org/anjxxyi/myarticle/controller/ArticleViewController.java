package org.anjxxyi.myarticle.controller;

import lombok.RequiredArgsConstructor;
import org.anjxxyi.myarticle.model.Article;
import org.anjxxyi.myarticle.model.dto.ArticleListViewResponse;
import org.anjxxyi.myarticle.model.dto.ArticleViewResponse;
import org.anjxxyi.myarticle.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // 컨트롤러라고 명시적으로 표시
@RequiredArgsConstructor
public class ArticleViewController {
    private final ArticleService articleService;

@GetMapping("/")
public String main() {
    return "redirect:/articles";
}

    // GET => 글 목록
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = articleService.getArticleAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    // GET => 글 조회
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }


    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = articleService.getArticle(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }


}
