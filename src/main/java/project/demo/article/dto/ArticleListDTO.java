package project.demo.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleListDTO {
    private Long id;
    private String author;
    private String title;
    private LocalDateTime CreateDate;
    private List<ArticleImage> articleImageList;

    public ArticleListDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.CreateDate = article.getCreateDate();
        this.author = article.getAuthor();
        this.articleImageList = article.getArticleImages();
    }
}
