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

        trackRepository.saveAll(List.of(
                new Track("GLASSY", "타이틀곡", "https://www.youtube.com/watch?v=8rd_E4cmcBg", glassy),
                new Track("Express Moon", "수록곡", "https://www.youtube.com/watch?v=YBNbIY1-xOU", glassy),
                new Track("가을 상자", "with 이석훈", "https://www.youtube.com/watch?v=PxuPUu4AJjQ", glassy),

                new Track("Round and Around", "수록곡", "https://youtu.be/example4", major),
                new Track("러브 쉿!", "타이틀곡", "https://youtu.be/example5", major),
                new Track("Rolla Skates", "수록곡", "https://youtu.be/example6", major),
                new Track("This Time", "수록곡", "https://youtu.be/example7", major),
                new Track("Opening", "조유리 자작곡", "https://youtu.be/example8", major),

                new Track("Loveable", "타이틀곡", "https://youtu.be/example9", minor),
                new Track("Blank", "수록곡", "https://youtu.be/example10", minor),
                new Track("Favorite Part", "수록곡", "https://youtu.be/example11", minor),

                new Track("TAXI", "타이틀곡", "https://youtu.be/example12", loveAll),
                new Track("Lemon Black Tea", "수록곡", "https://youtu.be/example13", loveAll),
                new Track("Bitter Taste", "수록곡", "https://youtu.be/example14", loveAll),
                new Track("Hang On", "수록곡", "https://youtu.be/example15", loveAll),
                new Track("Bruise", "수록곡", "https://youtu.be/example16", loveAll)
        ));
        for (int i = 1 ; i < 4 ; i++) {
            AlbumImage albumImage = AlbumImage.builder()
                    .url("/images/album/album" + i + ".png").album(albumRepository.findById((long)i).orElse(null)).build();
            albumImageRepository.save(albumImage);
        }

    }
}
