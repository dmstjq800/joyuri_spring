package project.demo.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.album.entity.AlbumImage;

public interface AlbumImageRepository extends JpaRepository<AlbumImage, Integer> {
}
