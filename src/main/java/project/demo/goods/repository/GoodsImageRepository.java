package project.demo.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.goods.entity.Goods;
import project.demo.goods.entity.GoodsImage;

import java.util.Optional;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
    Optional<GoodsImage> findByGoods(Goods goods);
}
