package project.demo.album.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.album.service.TrackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album/{id}")
public class TrackController {
    private final TrackService trackService;


}
