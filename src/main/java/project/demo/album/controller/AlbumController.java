package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> addAlbum(@RequestBody AlbumDTO albumDTO) {
        if (albumDTO.getTitle() == null) {return ResponseEntity.badRequest().body("Title is required");}
        return albumService.addAlbum(albumDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAlbumList() {
        return albumService.getAlbumList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable Long id) {
        return albumService.getAlbumDetailById(id);
    }

}
