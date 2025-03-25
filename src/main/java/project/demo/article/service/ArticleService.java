package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import project.demo.article.dto.ArticleDTO;
import project.demo.article.dto.ArticleDetailDTO;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.article.entity.Comment;
import project.demo.article.repository.ArticleRepository;
import project.demo.image.repository.ImageRepository;
import project.demo.image.service.ImageService;
import project.demo.member.dto.MemberDTO;
import project.demo.security.resultdata.RsData;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    /// 게시글 생성
    @Transactional
    public ResponseEntity<String> createArticle(ArticleDTO articleDTO, String nickname, MultipartFile image) {
        Article article = Article.builder()
                .title(articleDTO.getTitle())
                .content(articleDTO.getContent())
                .author(nickname)
                .comment(new ArrayList<>())
                .build();
        articleRepository.save(article);
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/");
            ArticleImage articleImage = ArticleImage.builder().article(article).url(url).build();
            

        }
        return ResponseEntity.ok("success");
    }
    /// 게시글 삭제
    @Transactional
    public ResponseEntity<String> deleteArticle(long id, String nickname) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) ResponseEntity.noContent().build();
        if(!article.getAuthor().equals(nickname)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");}
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
        ArticleDTO articleDTO = new ArticleDTO();
        for(int i = 0 ; i < 2 ;i ++){
            articleDTO.setTitle(String.valueOf("title" + i));
            articleDTO.setContent(String.valueOf("content" + i));
            createArticle(articleDTO, "test", null);
        }
    }


}
