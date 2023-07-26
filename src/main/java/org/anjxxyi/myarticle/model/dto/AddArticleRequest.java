package org.anjxxyi.myarticle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anjxxyi.myarticle.model.Article;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(String reqAuthor) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(reqAuthor)
                .build();
    }
}
