package project.demo.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.goods.entity.GoodsImage;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
}
