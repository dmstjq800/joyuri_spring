package project.demo.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.article.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByIdDesc();
}
