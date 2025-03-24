package project.demo.article.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime CreateDate;
    private LocalDateTime ModifyDate;
}
