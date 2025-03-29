package project.demo.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.article.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> getCommentsByArticle_Id(Long id);

    Optional<List<Comment>> findCommentByArticleIdAndParentIsNull(Long id);

    Optional<List<Comment>> findByParentId(long id);
}
    
