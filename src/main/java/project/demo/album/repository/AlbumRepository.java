package project.demo.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.album.entity.Album;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
