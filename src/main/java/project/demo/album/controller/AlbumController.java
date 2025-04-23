package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.dto.AlbumAddDTO;
import project.demo.album.entity.Album;
import project.demo.album.service.AlbumService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;

    /// 인가된 사용자만 가능
    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(AlbumAddDTO albumAddDTO,
                                      @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Album album = albumService.addAlbum(albumAddDTO, image);
        return ResponseEntity.ok("success : "+album.getId());
    }
    /// 앨범 리스트
    ///
    @GetMapping("/list")
    public ResponseEntity<?> getAlbumList(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(albumService.getAlbumList(pageable));
    }


    ///  앨범 디테일
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable long id) {
        return albumService.getAlbumDetailById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable long id) {
        Album album = albumService.deleteAlbum(id);
        return ResponseEntity.ok("success : "+album.getId());
    }

}
