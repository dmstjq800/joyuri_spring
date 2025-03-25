package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.dto.AlbumDTO;
import project.demo.album.service.AlbumService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;

    /// 인가된 사용자만 가능
    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(@RequestBody AlbumDTO albumDTO, MultipartFile image) {

        if (albumDTO.getTitle().isEmpty()) {return ResponseEntity.badRequest().body("Title is required");}
        return albumService.addAlbum(albumDTO, image);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAlbumList() {
        return albumService.getAlbumList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable String id) {
        return albumService.getAlbumDetailById(Long.parseLong(id));
    }

}
