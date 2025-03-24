package project.demo.article.dto;

import lombok.Getter;
import lombok.Setter;
import project.demo.article.entity.Article;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleListDTO {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime CreateDate;
    private LocalDateTime ModifyDate;

    public ArticleListDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.CreateDate = article.getCreateDate();
        this.ModifyDate = article.getModifyDate();
        this.author = article.getAuthor();
    }
}
