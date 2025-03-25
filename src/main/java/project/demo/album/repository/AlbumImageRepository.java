package project.demo.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.album.entity.AlbumImage;

public interface ImageRepository extends JpaRepository<AlbumImage, Integer> {
}
