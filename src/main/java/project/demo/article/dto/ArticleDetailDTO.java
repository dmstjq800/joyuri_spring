package project.demo.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.article.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleDetailDTO {

    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime CreateDate;
    private LocalDateTime ModifyDate;
    private List<ArticleImage> images;
    private List<Comment> comments;

    public ArticleDetailDTO(Article article) {
        this.id = article.getId();
        this.author = article.getAuthor();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.CreateDate = article.getCreateDate();
        this.ModifyDate = article.getModifyDate();
        this.comments = article.getComment();
        this.images = article.getArticleImages();

    }
}
