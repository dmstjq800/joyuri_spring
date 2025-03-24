package project.demo.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.album.repository.TrackRepository;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

}
