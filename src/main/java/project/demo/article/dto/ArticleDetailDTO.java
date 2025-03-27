package project.demo.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime CreateDate;
    private LocalDateTime ModifyDate;
    private List<ArticleImage> images;
    private List<Comment> comments;
    private long likes;

    public ArticleDetailDTO(Article article) {
        this.id = article.getId();
        this.author = article.getAuthor();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.CreateDate = article.getCreateDate();
        this.ModifyDate = article.getModifyDate();
        this.comments = article.getComment();
        this.images = article.getArticleImages();
        this.likes = article.getLikes();
    }
}
