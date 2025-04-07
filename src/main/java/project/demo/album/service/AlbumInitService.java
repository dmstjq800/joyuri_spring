package project.demo.album.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;
import project.demo.album.entity.Track;
import project.demo.album.repository.AlbumImageRepository;
import project.demo.album.repository.AlbumRepository;
import project.demo.album.repository.TrackRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumInitService {
    private final AlbumRepository albumRepository;
    private final AlbumImageRepository albumImageRepository;
    private final TrackRepository trackRepository;
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void saveJoYuriAlbums() {
        if(albumRepository.count() > 0){ return;}
        Album glassy = new Album("GLASSY", "조유리 첫 번째 싱글", LocalDate.of(2021, 10, 7), "Single");
        Album major = new Album("Op.22 Y-Waltz : in Major", "조유리 첫 번째 미니앨범", LocalDate.of(2022, 6, 2), "Mini");
        Album minor = new Album("Op.22 Y-Waltz : in Minor", "조유리 두 번째 싱글", LocalDate.of(2022, 10, 24), "Single");
        Album loveAll = new Album("LOVE ALL", "조유리 두 번째 미니앨범", LocalDate.of(2023, 8, 9), "Mini");
        albumRepository.saveAll(List.of(glassy, major, minor, loveAll));

        Album pickMeProd48 = new Album("PRODUCE 48 - 내꺼야 (PICK ME)", "Track: 내꺼야 (PICK ME)", LocalDate.of(2018, 5, 10), "Single");
        Album toReachYouProd48 = new Album("PRODUCE 48 - 30 Girls 6 Concepts", "Track: 너에게 닿기를 (To reach you)", LocalDate.of(2018, 8, 18), "Single");
        Album dreamingProd48 = new Album("PRODUCE 48 - FINAL", "Track: 꿈을 꾸는 동안 (IZ*ONE ver.)", LocalDate.of(2018, 9, 1), "Single");


        Album feverBasketOst = new Album("피버 바스켓 OST", "Track: FEVER", LocalDate.of(2019, 3, 5), "OST");
        Album myLoveBrahmsOst = new Album("브람스를 좋아하세요? OST Part.7", "Track: My Love", LocalDate.of(2020, 9, 22), "OST");
        Album storyOfUsMonthlyOst = new Album("월간 집 OST Part.2", "Track: STORY OF US", LocalDate.of(2021, 6, 24), "OST");


        Album maybeUniverse = new Album("모를 수도 있지만 (Maybe)", "Track: 모를 수도 있지만 (Maybe) - UNIVERSE MUSIC", LocalDate.of(2022, 8, 11), "Single");
        Album lawCafeOst = new Album("법대로 사랑하라 OST Part.2", "Track: 내 마음을 느끼나요", LocalDate.of(2022, 9, 13), "OST");
        Album fabulousOst = new Album("더 패뷸러스 OST Part.2", "Track: Run (Female Ver.)", LocalDate.of(2022, 12, 23), "OST");

        Album workLaterOst = new Album("술꾼도시여자들2 OST Part.5", "Track: Drink it, Girls! (적셔!) (with 한선화, 정은지)", LocalDate.of(2023, 1, 6), "OST");
        Album yellowCirclePepsi = new Album("Yellow Circle (2023 펩시 캠페인)", "Track: Yellow Circle (with 채수빈)", LocalDate.of(2023, 4, 13), "Single");
        Album seeYou19thOst = new Album("이번 생도 잘 부탁해 OST Part.3", "Track: DOWN", LocalDate.of(2023, 7, 2), "OST");


        Album poemForYouRemake = new Album("재해석 Vol.1", "Track: 그대라는 시 (원곡 태연) - 리메이크", LocalDate.of(2023, 7, 5), "Single");
        Album luvLuvLuvOst = new Album("소용없어 거짓말 OST Part.5", "Track: Luv Luv Luv (with 성한빈)", LocalDate.of(2023, 9, 5), "OST");
        Album lostDreamsSwfOst = new Album("스트릿 우먼 파이터2 계급미션 OST", "Track: Lost Dreams", LocalDate.of(2023, 9, 6), "OST");
        Album suriya = new Album("술이야", "Track: 술이야 - 리메이크", LocalDate.of(2023, 10, 12), "Single");

        Album myHighlightLoveSupremacyOst = new Album("여신강림 시즌1 OST Part.1", "Track: My Highlight", LocalDate.of(2024, 7, 24), "OST");
        Album windBlowsJeongnyeonOst = new Album("정년이 OST Part.3", "Track: 봄날은 간다", LocalDate.of(2024, 10, 27), "OST");
        albumRepository.saveAll(List.of(pickMeProd48, toReachYouProd48, dreamingProd48, feverBasketOst, maybeUniverse, myLoveBrahmsOst, storyOfUsMonthlyOst,
                lawCafeOst, fabulousOst, workLaterOst, yellowCirclePepsi, seeYou19thOst, poemForYouRemake, luvLuvLuvOst, lostDreamsSwfOst, suriya,
                myHighlightLoveSupremacyOst, windBlowsJeongnyeonOst));


        trackRepository.saveAll(List.of(
                new Track("GLASSY", "타이틀곡", "https://www.youtube.com/watch?v=8rd_E4cmcBg", glassy),
                new Track("Express Moon", "수록곡", "https://www.youtube.com/watch?v=YBNbIY1-xOU", glassy),
                new Track("가을 상자", "with 이석훈", "https://www.youtube.com/watch?v=PxuPUu4AJjQ", glassy),

                new Track("Round and Around", "수록곡", "https://www.youtube.com/watch?v=FaxX-rxUfL0", major),
                new Track("러브 쉿!", "타이틀곡", "https://www.youtube.com/watch?v=IxCb-GnpM_A", major),
                new Track("Rolla Skates", "수록곡", "https://www.youtube.com/watch?v=0UAMwqCyfwY", major),
                new Track("This Time", "수록곡", "https://www.youtube.com/watch?v=5UwiMaQFsIY", major),
                new Track("Opening", "조유리 자작곡", "https://www.youtube.com/watch?v=ItfMBf-Nvyk", major),

                new Track("Loveable", "타이틀곡", "https://www.youtube.com/watch?v=4zmWVaAZU-s", minor),
                new Track("Blank", "수록곡", "https://www.youtube.com/watch?v=asHqiElQXiM", minor),
                new Track("Favorite Part", "수록곡", "https://www.youtube.com/watch?v=galR8hC10vs", minor),

                new Track("TAXI", "타이틀곡", "https://www.youtube.com/watch?v=ZpGFolbbeHo", loveAll),
                new Track("Lemon Black Tea", "수록곡", "https://www.youtube.com/watch?v=6ueWGSVZBlE", loveAll),
                new Track("Bitter Taste", "수록곡", "https://www.youtube.com/watch?v=as9a46yINhI", loveAll),
                new Track("Hang On", "수록곡", "https://www.youtube.com/watch?v=MkjMdPTC6H8", loveAll),
                new Track("멍", "수록곡", "https://www.youtube.com/watch?v=6mlYX3SMGNI", loveAll),

                new Track("내꺼야 (PICK ME)", "Produce48", "https://www.youtube.com/watch?v=UrdA9YHrmQs", pickMeProd48),
                new Track("너에게 닿기를 (To reach you)", "Produce48", "https://www.youtube.com/watch?v=n5peb_o-mkA", toReachYouProd48),
                new Track("꿈을 꾸는 동안 (IZ*ONE ver.)", "Produce48", "https://www.youtube.com/watch?v=6shHDae9We4", dreamingProd48),
                new Track("FEVER", "피버 바스켓 OST", "https://www.youtube.com/watch?v=hACKDlCdsGs", feverBasketOst),
                new Track("My Love", "브람스를 좋아하세요? OST Part.7", "https://www.youtube.com/watch?v=WDMdSPOve1I", myLoveBrahmsOst),
                new Track("STORY OF US", "월간 집 OST Part.2", "https://www.youtube.com/watch?v=W2cE9IEYP-Y", storyOfUsMonthlyOst),
                new Track("모를 수도 있지만 (Maybe)", "모를 수도 있지만 (Maybe)", "https://www.youtube.com/watch?v=jJlOcE3AaRs", maybeUniverse),
                new Track("내 마음을 느끼나요", "법대로 사랑하라 OST Part.2", "https://www.youtube.com/watch?v=8XngmnkdmN4", lawCafeOst),
                new Track("Run (Female Ver.)", "더 패뷸러스 OST Part.2", "https://www.youtube.com/watch?v=1NqwfqnIb1A", fabulousOst),

                new Track("Drink it, Girls! (적셔!) (with 한선화, 정은지)", "술꾼도시여자들2 OST Part.5", "https://www.youtube.com/watch?v=fNi_AjNUpao", workLaterOst),
                new Track("Yellow Circle (with 채수빈)", "Yellow Circle (2023 펩시 캠페인)", "https://www.youtube.com/watch?v=emUfJ_LCyuw", yellowCirclePepsi),
                new Track("DOWN", "이번 생도 잘 부탁해 OST Part.3", "https://www.youtube.com/watch?v=a7z28ZjYR10", seeYou19thOst),
                new Track("그대라는 시", "재해석 Vol.1", "https://www.youtube.com/watch?v=7ZJJypOu1sY", poemForYouRemake),
                new Track("Luv Luv Luv (with 성한빈)", "소용없어 거짓말 OST Part.5", "https://www.youtube.com/watch?v=auKuB-WADl8", luvLuvLuvOst),
                new Track("Lost Dreams", "스트릿 우먼 파이터2 계급미션 OST", "https://www.youtube.com/watch?v=Bot1vR88hSk", lostDreamsSwfOst),
                new Track("술이야 - 리메이크", "술이야", "https://www.youtube.com/watch?v=GAi_L04mfXk", suriya),
                new Track("My Highlight", "여신강림 시즌1 OST Part.1", "https://www.youtube.com/watch?v=JxFG_9vtn0Q", myHighlightLoveSupremacyOst),
                new Track("봄날은 간다", "정년이 OST Part.3", "https://www.youtube.com/watch?v=RDwR4BfQkK0", windBlowsJeongnyeonOst)

        ));
        for (int i = 1 ; i < 23 ; i++) {
            AlbumImage albumImage = AlbumImage.builder()
                    .url("/images/album/album" + i + ".png").album(albumRepository.findById((long)i).orElse(null)).build();
            albumImageRepository.save(albumImage);
        }

    }
}
