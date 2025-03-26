package project.demo.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
