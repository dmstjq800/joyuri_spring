package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.article.repository.ArticleImageRepository;
import project.demo.article.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleInitService {
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;


    /// init service
    @EventListener(ApplicationReadyEvent.class)
    public void initarticle(){
        if(articleRepository.count() > 0) return;
        for(int i = 1; i < 12; i++){
            Article article = Article.builder()
                    .title("율 사진 " + i)
                    .content("쪼율 일상 사진 " + i)
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
