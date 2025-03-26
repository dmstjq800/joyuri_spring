package project.demo.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;

import java.util.Optional;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
    Optional <ArticleImage> findByArticle(Article article);
}
