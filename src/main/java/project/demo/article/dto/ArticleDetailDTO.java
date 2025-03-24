package project.demo.article.dto;

import lombok.Getter;
import lombok.Setter;
import project.demo.article.entity.Article;
import project.demo.article.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArticleDetailDTO {

    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime CreateDate;
    private LocalDateTime ModifyDate;

    private List<Comment> comments;

    public ArticleDetailDTO(Article article) {
        this.id = article.getId();
        this.author = article.getAuthor();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.CreateDate = article.getCreateDate();
        this.ModifyDate = article.getModifyDate();
        this.comments = article.getComment();

    }
}
