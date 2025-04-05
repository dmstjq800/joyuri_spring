package project.demo.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.demo.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;


    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private Article article;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime CreateDate;

    // 부모 댓글
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore //
    private Comment parent;

    // 자식 댓글 (대댓글 목록)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    // 편의 메소드 (자식 댓글 추가)
    public void addChild(Comment child) {
        children.add(child);
        child.setParent(this);
    }

}