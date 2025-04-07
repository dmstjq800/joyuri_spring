package project.demo.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.concert.entity.Concert;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findAllByOrderByIdDesc();
}
