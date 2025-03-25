package project.demo.article.dto;


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
public class CommentDTO {
    private Long id;
    private String author;
    private String content;
    private LocalDateTime CreateDate;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.CreateDate = comment.getCreateDate();
    }
}
