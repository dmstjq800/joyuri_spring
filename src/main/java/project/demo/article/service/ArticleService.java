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
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.article.entity.Comment;
import project.demo.article.repository.ArticleImageRepository;
import project.demo.article.repository.ArticleRepository;
import project.demo.album.repository.AlbumImageRepository;
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
    private final MemberRepository memberRepository;

    /// 게시글 생성
    @Transactional
    public ResponseEntity<String> createArticle(ArticleDTO articleDTO, String nickname, MultipartFile image) {
        Article article = Article.builder()
                .title(articleDTO.getTitle())
                .content(articleDTO.getContent())
                .author(nickname)
                .comment(new ArrayList<>())
                .articleImages(new ArrayList<>())
                .build();
        articleRepository.save(article);
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/");
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
    /// 게시글 수정
    public ResponseEntity<String> editAticle(long id, ArticleDTO articleDTO, MultipartFile image) {
        String nickname = memberService.getCurrentNickname();
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) ResponseEntity.notFound().build();
        if(!article.getAuthor().equals(nickname)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");}
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/");
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
    public ResponseEntity<?> getArticlesPerPage(int page) {
        int pageSize = 9;

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Article> articlePage = articleRepository.findAll(pageable);
        List<Article> articles = articlePage.getContent();
        List<ArticleListDTO> articleListDTO = new ArrayList<>();
        for(Article article : articles){
            articleListDTO.add(new ArticleListDTO(article));
        }
        return ResponseEntity.ok(articleListDTO);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void initarticle(){
        for(int i = 0; i < 200; i ++){
            Article article = Article.builder()
                    .author("test")
                    .content("test")
                    .title("test")
                    .build();
            articleRepository.save(article);
            ArticleImage articleImage = ArticleImage.builder()
                    .article(article)
                    .url("/images/article/article" + (i%5+1) + ".png")
                    .build();
            articleImageRepository.save(articleImage);
        }
        for(int i = 1; i < 6; i++){
            Article article = Article.builder()
                    .title("오늘 하루도 즐겁게 " + i)
                    .content("대충 오늘 재밌었다는 얘기 " + i)
                    .author("JJoYul")
                    //.member(memberRepository.findByNickname("JJoYul"))
                    .build();
            articleRepository.save(article);
            ArticleImage articleImage = ArticleImage.builder()
                    .article(article)
                    .url("/images/article/article" + i + ".png")
                    .build();
            articleImageRepository.save(articleImage);
        }
    }

}
