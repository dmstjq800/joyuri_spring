package project.demo.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.dto.AlbumDTO;
import project.demo.album.dto.AlbumDetailDTO;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;
import project.demo.image.repository.ImageRepository;
import project.demo.image.service.ImageService;
import project.demo.album.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    /// 앨범 추가
    public ResponseEntity<?> addAlbum(AlbumDTO albumDTO, MultipartFile image) {
        Album album = Album.builder()
                .title(albumDTO.getTitle())
                .description(albumDTO.getDescription())
                .build();
        albumRepository.save(album);
        if(image != null) {
            String url = imageService.ImageUpload(image,"album/");
            AlbumImage albumImage = AlbumImage.builder().url(url).build();
            imageRepository.save(albumImage);
            album.getAlbumImages().add(albumImage);
        }

        return ResponseEntity.ok("Album added");
    }
    /// 앨범 겟
    public ResponseEntity<?> getAlbumDetailById(long id) {
        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) {return ResponseEntity.status(404).body("Album not found");}
        return ResponseEntity.ok(new AlbumDetailDTO(album));


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
