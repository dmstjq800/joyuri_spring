package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.article.dto.ArticleDTO;
import project.demo.article.dto.ArticleDetailDTO;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.dto.CommentDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.article.entity.ArticleLike;
import project.demo.article.entity.Comment;
import project.demo.article.repository.ArticleImageRepository;
import project.demo.article.repository.ArticleRepository;
import project.demo.album.repository.AlbumImageRepository;
import project.demo.article.repository.CommentRepository;
import project.demo.image.service.ImageService;
import project.demo.member.repository.MemberRepository;
import project.demo.member.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ImageService imageService;
    private final ArticleImageRepository articleImageRepository;
    private final MemberService memberService;
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final ArticleLikeService articleLikeService;

    /// 게시글 생성
    @Transactional
    public ResponseEntity<String> createArticle(ArticleDTO articleDTO, String nickname, MultipartFile image) {
        Article article = Article.builder()
                .title(articleDTO.getTitle())
                .content(articleDTO.getContent())
                .author(nickname)
                .build();
        articleRepository.save(article);
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/", article.getId());
            ArticleImage articleImage = ArticleImage.builder().article(article).url(url).build();
            articleImageRepository.save(articleImage);
            article.getArticleImages().add(articleImage);
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
    public ResponseEntity<?> getArticleDetail(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        boolean liked = articleLikeService.liked(article);

        List<CommentDTO> commentDTOList = commentService.getCommentList(id);
        return ResponseEntity.ok(new ArticleDetailDTO(article, commentDTOList, liked));
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

    public Article findById(long id) {
        return articleRepository.findById(id).orElse(null);
    }

    /// 게시글 수정
    public ResponseEntity<String> editAticle(long id, ArticleDTO articleDTO, MultipartFile image) {
        String nickname = memberService.getCurrentNickname();
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) ResponseEntity.notFound().build();
        if(!article.getAuthor().equals(nickname)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");}
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/", article.getId());
            ArticleImage articleImage = articleImageRepository.findByArticle(article).orElse(null);
            if(articleImage == null) {
                articleImage = ArticleImage.builder().article(article).url(url).build();
            }
            else articleImage.setUrl(url);
            articleImageRepository.save(articleImage);
            articleRepository.save(article);
        }return ResponseEntity.ok("success");
    }
    /// 상위 4개 표시
    public ResponseEntity<?> getArticle4entity() {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().limit(4).toList();
        List<ArticleListDTO> articleListDTO = new ArrayList<>();
        for(Article article : articles){
            articleListDTO.add(new ArticleListDTO(article));
        }
        return ResponseEntity.ok(articleListDTO);
    }
    /// 페이징 조회
    public ResponseEntity<?> getArticlesPerPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Article> articlePage = articleRepository.findAll(pageable);
        List<Article> articles = articlePage.getContent();
        List<ArticleListDTO> articleListDTO = new ArrayList<>();
        for(Article article : articles){
            articleListDTO.add(new ArticleListDTO(article));
        }
        return ResponseEntity.ok(articleListDTO);
    }

}
