package project.demo.goods.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.goods.entity.Goods;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
//    List<Goods> findAllByOrderByIdDesc();
//    //오름
//    List<Goods> findAllByOrderByPriceAsc();
//    //내림
//    List<Goods> findAllByOrderByPriceDesc();


    Page<Goods> findByGoodsNameContainingIgnoreCaseOrderByPriceAsc(String name, Pageable pageable);

    Page<Goods> findByGoodsNameContainingOrderByIdDesc(String name, Pageable pageable);

    Page<Goods> findByGoodsNameContainingIgnoreCaseOrderByPriceDesc(String name, Pageable pageable);

    Page<Goods> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Goods> findAllByOrderByPriceDesc(Pageable pageable);

    Page<Goods> findAllByOrderByIdDesc(Pageable pageable);

    Page<Goods> findAllByOrderById(Pageable pageable);
}
