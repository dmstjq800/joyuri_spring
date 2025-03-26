package project.demo.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.career.entity.CareerImage;

public interface CareerImageRepository extends JpaRepository<CareerImage, Long> {
}
