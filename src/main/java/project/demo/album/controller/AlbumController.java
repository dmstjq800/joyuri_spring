package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.dto.AlbumDTO;
import project.demo.album.service.AlbumService;
import project.demo.article.entity.ArticleImage;
import project.demo.article.repository.ArticleRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;
    private final ArticleRepository articleRepository;

    /// 인가된 사용자만 가능
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")
    public ResponseEntity<?> addAlbum(@RequestBody AlbumDTO albumDTO, @RequestParam(value = "image", required = false) MultipartFile image) {
        if (albumDTO.getTitle().isEmpty()) {return ResponseEntity.badRequest().body("Title is required");}
        return albumService.addAlbum(albumDTO, image);
    }
    /// 앨범 리스트
    ///
    @GetMapping("/list")
    public ResponseEntity<?> getAlbumList() {
        return albumService.getAlbumList();
    }


    ///  앨범 디테일
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable String id) {
        return albumService.getAlbumDetailById(Long.parseLong(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAlbum(String id) {
        return albumService.deleteAlbum(Long.parseLong(id));
    }

}
