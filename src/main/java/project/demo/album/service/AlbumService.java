package project.demo.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.dto.AlbumDTO;
import project.demo.album.dto.AlbumDetailDTO;
import project.demo.album.dto.PageResponseDTO;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;
import project.demo.album.repository.AlbumImageRepository;
import project.demo.goods.entity.Goods;
import project.demo.image.service.ImageService;
import project.demo.album.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ImageService imageService;
    private final AlbumImageRepository albumImageRepository;

    public ResponseEntity<?> deleteAlbum(long id) {
        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) return ResponseEntity.notFound().build();
        albumRepository.delete(album);
        return ResponseEntity.ok("success");
    }

    /// 앨범 추가
    public ResponseEntity<?> addAlbum(AlbumDTO albumDTO, MultipartFile image) {
        Album album = Album.builder()
                .title(albumDTO.getTitle())
                .description(albumDTO.getDescription())
                .build();
        albumRepository.save(album);
        if (image != null) {
            String url = imageService.ImageUpload(image, "album/", album.getId());
            AlbumImage albumImage = AlbumImage.builder().url(url).build();
            albumImageRepository.save(albumImage);
            album.getAlbumImages().add(albumImage);
        }

        return ResponseEntity.ok("Album added");
    }

    /// 앨범 겟
    public ResponseEntity<?> getAlbumDetailById(long id) {
        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) {
            return ResponseEntity.status(404).body("Album not found");
        }
        return ResponseEntity.ok(new AlbumDetailDTO(album));


    }


    /// 앨범리스트 겟
    public ResponseEntity<List<AlbumDTO>> getAlbumList() {
        List<Album> albumList = albumRepository.findAllByOrderByIdDesc();
        List<AlbumDTO> list = new ArrayList<>();
        for (Album album : albumList) {
            list.add(new AlbumDTO(album));
        }

        return ResponseEntity.ok(list);
    }
}

