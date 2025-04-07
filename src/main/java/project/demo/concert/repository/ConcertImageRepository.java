package project.demo.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.concert.entity.ConcertImage;

public interface ConcertImageRepository extends JpaRepository<ConcertImage, Long> {
}
