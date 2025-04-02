package project.demo.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.album.dto.TrackDTO;
import project.demo.album.entity.Album;
import project.demo.album.entity.Track;
import project.demo.album.repository.AlbumRepository;
import project.demo.album.repository.TrackRepository;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    /// 트랙추가
    public ResponseEntity<?> addTrack(Long id, TrackDTO trackDTO) {
        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) {return ResponseEntity.status(404).body("Album not found");}
        Track track = Track.builder()
                .title(trackDTO.getTitle())
                .description(trackDTO.getDescription())
                .youtubeUrl(trackDTO.getYoutubeUrl())
                .album(album).build();
        trackRepository.save(track);
        album.getTracks().add(track);
        return ResponseEntity.ok("Added track");
    }
}
