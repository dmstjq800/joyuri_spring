package project.demo.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.album.dto.TrackAddDTO;
import project.demo.album.dto.TrackResponseDTO;
import project.demo.album.entity.Album;
import project.demo.album.entity.Track;
import project.demo.album.repository.AlbumRepository;
import project.demo.album.repository.TrackRepository;
import project.demo.security.exeption.customexception.BadRequestException;
import project.demo.security.exeption.customexception.NotFoundException;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    /// 트랙추가
    public Track addTrack(long id, TrackAddDTO trackAddDTO) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundException("album not found"));
        if(trackAddDTO.getTitle() == null) throw new BadRequestException("title is empty");
        if (album == null) {throw new NotFoundException("Album not found");}
        Track track = Track.builder()
                .title(trackAddDTO.getTitle())
                .description(trackAddDTO.getDescription())
                .youtubeUrl(trackAddDTO.getYoutubeUrl())
                .album(album).build();
        trackRepository.save(track);
        album.getTracks().add(track);
        return track;
    }

    public Track deleteTrack(long trackId) {
        Track track = trackRepository.findById(trackId).orElseThrow(() -> new NotFoundException("track not found"));
        trackRepository.delete(track);
        return track;
    }
}
