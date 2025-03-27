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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InitService {
    private final AlbumRepository albumRepository;
    private final AlbumImageRepository albumImageRepository;
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void saveJoYuriAlbums() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // 1. GLASSY (2021.10.07)
        Album glassy = new Album("GLASSY", LocalDate.parse("2021.10.07", formatter), "조유리 첫 번째 싱글 앨범");
        glassy.addTrack(new Track("GLASSY", "유리처럼 맑고 다채로운 매력을 담은 곡", "https://www.youtube.com/watch?v=example1"));
        glassy.addTrack(new Track("Express Moon", "몽환적인 분위기의 수록곡", "https://www.youtube.com/watch?v=example2"));
        glassy.addTrack(new Track("가을 상자 (Fall In Love)", "가을 감성이 느껴지는 곡", "https://www.youtube.com/watch?v=example3"));

        albumRepository.save(glassy);

        // 2. Op.22 Y-Waltz : in Minor (2022.06.02)
        Album inMinor = new Album("Op.22 Y-Waltz : in Minor", LocalDate.parse("2022.06.02", formatter), "조유리 첫 번째 미니 앨범");
        inMinor.addTrack(new Track("Round and Around", "신비로운 느낌의 인트로", "https://www.youtube.com/watch?v=example4"));
        inMinor.addTrack(new Track("러브 쉿! (Love Shhh!)", "통통 튀는 매력의 타이틀곡", "https://www.youtube.com/watch?v=example5"));
        inMinor.addTrack(new Track("Roller Coaster", "사랑의 감정을 롤러코스터에 비유한 곡", "https://www.youtube.com/watch?v=example6"));
        inMinor.addTrack(new Track("This Time", "감성적인 발라드 트랙", "https://www.youtube.com/watch?v=example7"));
        inMinor.addTrack(new Track("물고기 (Opening)", "앨범의 시작을 알리는 트랙", "https://www.youtube.com/watch?v=example8"));
        albumRepository.save(inMinor);

        // 3. Op.22 Y-Waltz : in Major (2022.10.24)
        Album inMajor = new Album("Op.22 Y-Waltz : in Major part.2", LocalDate.parse("2022.10.24", formatter), "조유리 두 번째 미니 앨범");
        inMajor.addTrack(new Track("Taxi", "강렬한 퍼포먼스가 돋보이는 타이틀곡", "https://www.youtube.com/watch?v=example9"));
        // 추가 트랙 정보 필요
        albumRepository.save(inMajor);

        // 4. LOVE ALL (2023.08.09)
        Album loveAll = new Album("LOVE ALL", LocalDate.parse("2023.08.09", formatter), "조유리 두 번째 싱글 앨범");
        loveAll.addTrack(new Track("TAKE MY HAND", "청량한 분위기의 타이틀곡", "https://www.youtube.com/watch?v=example10"));
        loveAll.addTrack(new Track("Lemon Black Tea", "상큼한 매력의 수록곡", "https://www.youtube.com/watch?v=example11"));
        loveAll.addTrack(new Track("조각 (Pieces)", "감성적인 분위기의 곡", "https://www.youtube.com/watch?v=example12"));
        loveAll.addTrack(new Track("물감 (Color)", "다채로운 색깔을 표현한 곡", "https://www.youtube.com/watch?v=example13"));
        loveAll.addTrack(new Track("나의 정원 (My Garden)", "따뜻한 느낌의 곡", "https://www.youtube.com/watch?v=example14"));
        albumRepository.save(loveAll);

        // 5. [싱글] BOX (2024.05.24)
        Album boxSingle = new Album("BOX", LocalDate.parse("2024.05.24", formatter), "조유리 싱글 앨범");
        boxSingle.addTrack(new Track("BOX", "독특한 콘셉트의 타이틀곡", "https://www.youtube.com/watch?v=example15"));
        boxSingle.addTrack(new Track("Secret", "비밀스러운 분위기의 수록곡", "https://www.youtube.com/watch?v=example16"));
        albumRepository.save(boxSingle);

        for(int i = 1; i <= 5; i++) {
            AlbumImage albumImage = AlbumImage.builder()
                    .album(albumRepository.findById((long)i).orElse(null))
                    .url("/images/album/album" + i + ".png")
                    .build();
            albumImageRepository.save(albumImage);
        }

    }
}
