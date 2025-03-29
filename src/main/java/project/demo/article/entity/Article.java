package project.demo.article.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.demo.album.entity.AlbumImage;
import project.demo.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String author;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "author", referencedColumnName = "nickname")
    // private Member author;


    @CreatedDate
    @Column(updatable = false)

    private LocalDateTime CreateDate;

    @LastModifiedDate

    private LocalDateTime ModifyDate;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleImage> articleImages = new ArrayList<>();


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();

    private long likes;

}
