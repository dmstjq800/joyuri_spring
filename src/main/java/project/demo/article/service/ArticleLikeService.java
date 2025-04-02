package project.demo.article.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleLike;
import project.demo.article.repository.ArticleLikeRepository;
import project.demo.article.repository.ArticleRepository;
import project.demo.member.entity.Member;
import project.demo.member.repository.MemberRepository;
import project.demo.member.service.MemberService;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    /// 좋아요
    public ResponseEntity<?> like(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        Member member = memberService.findByusername(memberService.getCurrentUsername());

        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndMemberId(id, member.getId()).orElse(null);
        if(articleLike == null) {
            articleLike = ArticleLike.builder()
                    .article(article)
                    .member(member)
                    .build();
            article.setLikes(article.getLikes() + 1);
            articleLikeRepository.save(articleLike);
            articleRepository.save(article);
            return ResponseEntity.ok("좋아요! 현재 좋아요 수: " + article.getLikes());
        }
        else{
            articleLikeRepository.delete(articleLike);
            article.setLikes(article.getLikes() - 1);
            articleRepository.save(article);
            return ResponseEntity.ok("좋아요 취소, 현재 좋아요 수: " + article.getLikes());
        }
    }
    /// 좋아요 확인
    public boolean liked(Article article) {
        Member member = memberService.findByusername(memberService.getCurrentUsername());
        if(member == null) {return false;}
        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndMemberId(article.getId(), member.getId()).orElse(null);
        return articleLike != null;

    }
}
