package project.demo.article.entity;


import jakarta.persistence.*;
import lombok.*;
import project.demo.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // User 엔티티가 있다고 가정합니다.


    public ArticleLike(Article article, Member member) {
        this.article = article;
        this.member = member;
    }
}
