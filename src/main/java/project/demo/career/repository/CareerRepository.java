package project.demo.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.career.entity.Career;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findAllByOrderByIdDesc();
}
