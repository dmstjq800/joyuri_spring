package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.album.dto.TrackAddDTO;
import project.demo.album.dto.TrackResponseDTO;
import project.demo.album.entity.Track;
import project.demo.album.service.TrackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class TrackController {
    private final TrackService trackService;
    /// 인가된 사용자만 가능
    @PostMapping("/{albumId}/addtrack")
    public ResponseEntity<?> addTrack(@PathVariable long albumId, @RequestBody TrackAddDTO trackAddDTO) {
        Track track = trackService.addTrack(albumId, trackAddDTO);
        return ResponseEntity.ok("success : "+ track.getId());
    }
    @DeleteMapping("/delete/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable long trackId) {
        Track track = trackService.deleteTrack(trackId);
        return ResponseEntity.ok("success : "+ track.getId());
    }
}
