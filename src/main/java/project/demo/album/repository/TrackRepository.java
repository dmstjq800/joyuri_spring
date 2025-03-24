package project.demo.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.album.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
