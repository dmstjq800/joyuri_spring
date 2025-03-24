package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import project.demo.article.dto.ArticleDTO;
import project.demo.article.dto.ArticleDetailDTO;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.Comment;
import project.demo.article.repository.ArticleRepository;
import project.demo.security.resultdata.RsData;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    /// 게시글 생성
    @Transactional
    public ResponseEntity<String> createArticle(String title, String content, String username) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(username)
                .build();
        articleRepository.save(article);
        return ResponseEntity.ok("success");
    }
    /// 게시글 삭제
    @Transactional
    public ResponseEntity<String> deleteArticle(long id, String username) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) ResponseEntity.noContent().build();
        if(!article.getAuthor().equals(username)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");}
        articleRepository.delete(article);
        return ResponseEntity.ok("success");
    }
    /// 게시글 상세 조회
    public ArticleDetailDTO getArticleDetail(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return new ArticleDetailDTO(article);
    }
    /// 게시글 ListDTO 반환 ///
    public List<ArticleListDTO> getArticlelist() {
        List<Article> articles = articleRepository.findAllByOrderByIdDesc();
        List<ArticleListDTO> articleListDTO = new ArrayList<>();
        for (Article article : articles) {
            articleListDTO.add(new ArticleListDTO(article));
        }
        return articleListDTO;
    }

    /// 게시글 역순 조회
    public List<Article> findAllByOrderByIdDesc() {
        return articleRepository.findAllByOrderByIdDesc();
    }
    ///  게시글 조회
    public Article findById(long id) {
        return articleRepository.findById(id).orElse(null);
    }
    /// 게시글 댓글 조회
    public List<Comment> getCommentsByArticleId(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        List<Comment> comments = article.getComment();
        return comments;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initarticle(){
        for(int i = 0 ; i < 2 ;i ++){
            String title = "title" + i;
            String content = "content" + i;
            createArticle(title, content, "test");
        }
    }


}
