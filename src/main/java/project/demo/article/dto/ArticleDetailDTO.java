package project.demo.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;

import java.time.LocalDateTime;
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
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime modifyDate;
    private List<ArticleImage> images;
    private List<CommentResponseDTO> comments;
    private int commentCount;
    private long likes;
    private boolean liked;


    public ArticleDetailDTO(Article article, List<CommentResponseDTO> commentResponseDTOList, Boolean liked) {
        this.id = article.getId();
        this.author = article.getAuthor();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createDate = article.getCreateDate();
        this.modifyDate = article.getModifyDate();
        this.comments = commentResponseDTOList;
        this.images = article.getArticleImages();
        this.likes = article.getLikes();
        this.commentCount = article.getComment().size();
        this.liked = liked;
    }
}
