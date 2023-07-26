package org.anjxxyi.myarticle.model.dto;

import lombok.Data;
import org.anjxxyi.myarticle.model.Article;

@Data
public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getAuthor();
    }
}
