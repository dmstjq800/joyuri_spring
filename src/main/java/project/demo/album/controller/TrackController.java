package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.album.dto.TrackDTO;
import project.demo.album.service.TrackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album/{id}")
public class TrackController {
    private final TrackService trackService;
    /// 인가된 사용자만 가능
    @PostMapping("/addTrack")
    public ResponseEntity<?> addTrack(@PathVariable String id, @RequestBody TrackDTO trackDTO) {
        if(trackDTO.getTitle() == null || trackDTO.getTitle().isEmpty()) {return ResponseEntity.status(400).body("Title cannot be empty");}
        return trackService.addTrack(Long.parseLong(id), trackDTO);
    }
}
