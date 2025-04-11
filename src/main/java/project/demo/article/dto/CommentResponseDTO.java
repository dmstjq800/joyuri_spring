package project.demo.article.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.article.entity.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String author;
    private String content;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime createDate;
    private int children;

    public CommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor().getNickname();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.children = comment.getChildren().size();
    }
}
