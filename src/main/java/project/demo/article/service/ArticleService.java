package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.article.dto.*;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.article.repository.ArticleImageRepository;
import project.demo.article.repository.ArticleRepository;
import project.demo.article.repository.CommentRepository;
import project.demo.image.service.ImageService;
import project.demo.member.service.MemberService;
import project.demo.security.exeption.customexception.ForbiddenException;
import project.demo.security.exeption.customexception.BadRequestException;
import project.demo.security.exeption.customexception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
    public Article createArticle(ArticleRequestDTO articleRequestDTO, MultipartFile image) {
        if(articleRequestDTO.getTitle().isEmpty()) throw new BadRequestException("Title cannot be empty");
        Article article = Article.builder()
                .title(articleRequestDTO.getTitle())
                .content(articleRequestDTO.getContent())
                .author(memberService.getCurrentNickname())
                .build();
        articleRepository.save(article);
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/", article.getId());
            ArticleImage articleImage = ArticleImage.builder().article(article).url(url).build();
            articleImageRepository.save(articleImage);
        }
        return article;
    }
    /// 게시글 삭제
    @Transactional
    public Article deleteArticle(long id) {
        Article article = articleRepository.findById(id).orElseThrow( () -> new NotFoundException("article not found"));
        articleRepository.delete(article);
        return article;
    }
    /// 게시글 상세 조회
    public ArticleDetailDTO getArticleDetail(long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("article not found"));
        boolean liked = articleLikeService.liked(article);
        List<CommentDTO> commentDTOList = commentService.getCommentList(id);
        return new ArticleDetailDTO(article, commentDTOList, liked);
    }

    public Article findById(long id) {
        return articleRepository.findById(id).orElse(null);
    }

    /// 게시글 수정
    public Article editArticle(long id, ArticleRequestDTO articleRequestDTO, MultipartFile image) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("article not found"));
        article.setTitle(articleRequestDTO.getTitle());
        article.setContent(articleRequestDTO.getContent());
        if(image != null) {
            String url = imageService.ImageUpload(image, "article/", article.getId());
            ArticleImage articleImage = articleImageRepository.findByArticle(article).orElse(null);
            if(articleImage == null) {
                articleImage = ArticleImage.builder().article(article).url(url).build();
            }
            else articleImage.setUrl(url);
            articleImageRepository.save(articleImage);
            articleRepository.save(article);
        }return article;
    }
    /// 상위 4개 표시
    public List<ArticleListDTO> getArticle4entity() {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().limit(4).toList();
        return articles.stream().map(ArticleListDTO::new).collect(Collectors.toList());
    }
    /// 페이징 조회
    public List<ArticleListDTO> getArticlesPerPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Article> articlePage = articleRepository.findAll(pageable);
        List<ArticleListDTO> articlelist = articlePage.getContent().stream().map(ArticleListDTO::new).collect(Collectors.toList());
        //new ArticlePageDTO(articlePage, articlelist);
        return articlelist;
    }

}
