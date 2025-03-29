package project.demo.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleLike;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Optional<ArticleLike> findByArticleIdAndMemberId(Long articleId, Long memberId);
    void deleteByArticleIdAndMemberId(Long articleId, Long memberId);
    long countByArticleId(Long articleId); // 특정 게시글의 좋아요 수 조회 (선택 사항)
    Optional<ArticleLike> findByArticle(Article article);
}
