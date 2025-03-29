package project.demo.article.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.article.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String author;
    private String content;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime createDate;
    private int children;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.children = comment.getChildren().size();
    }
}
