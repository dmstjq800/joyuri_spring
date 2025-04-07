package project.demo.article.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import project.demo.article.entity.Article;

import java.util.List;

@Getter
@Setter
public class ArticlePageDTO {
    List<ArticleListDTO> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;
    public ArticlePageDTO(Page<Article> articlePage, List<ArticleListDTO> content) {
        this.content = content;
        this.pageNumber = articlePage.getNumber();
        this.pageSize = articlePage.getSize();
        this.totalElements = articlePage.getTotalElements();
        this.totalPages = articlePage.getTotalPages();
        this.first = articlePage.isFirst();
        this.last = articlePage.isLast();
    }
}
