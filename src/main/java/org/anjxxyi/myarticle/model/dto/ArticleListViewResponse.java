package org.anjxxyi.myarticle.model.dto;

import lombok.Getter;
import org.anjxxyi.myarticle.model.Article;

@Getter
public class ArticleListViewResponse {
    private Long id;
    private String title;
    private String content;
    private String author;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getAuthor();
    }
}
