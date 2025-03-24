package project.demo.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.album.dto.AlbumDTO;
import project.demo.album.entity.Album;
import project.demo.album.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    /// 앨범 추가
    public ResponseEntity<?> addAlbum(AlbumDTO albumDTO) {
        Album album = Album.builder()
                .title(albumDTO.getTitle())
                .description(albumDTO.getDescription())
                .image(albumDTO.getImage())
                .build();
        albumRepository.save(album);
        return ResponseEntity.ok(album);
    }
    /// 앨범 겟
    public ResponseEntity<?> getAlbumDetailById(long id) {
        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) {return ResponseEntity.status(404).body("Album not found");}
        return ResponseEntity.ok(new AlbumDTO(album));


    }
    /// 앨범리스트 겟
    public ResponseEntity<List<AlbumDTO>> getAlbumList() {
        List<Album> albumList = albumRepository.findAll();
        List<AlbumDTO> list = new ArrayList<>();
        for (Album album : albumList) {
            list.add(new AlbumDTO(album));
        }
        return ResponseEntity.ok(list);
    }
}
