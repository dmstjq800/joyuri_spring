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

    List<Goods> findAllByGoodsNameContainingIgnoreCase(String goodsName);
    List<Goods> findByGoodsNameContainingIgnoreCaseOrderByIdDesc(String goodsName);

    /// 이름 + 가격 오름차
    List<Goods> findAllByGoodsNameContainingIgnoreCaseOrderByPriceAsc(String goodsName);
    /// 이름 + 가격 내림차
    List<Goods> findAllByGoodsNameContainingIgnoreCaseOrderByPriceDesc(String goodsName);

    Page<Goods> findByGoodsNameContainingIgnoreCaseOrderByPriceAsc(String name, Pageable pageable);

    Page<Goods> findByGoodsNameContaining(String name, Pageable pageable);

    Page<Goods> findByGoodsNameContainingIgnoreCaseOrderByPriceDesc(String name, Pageable pageable);

    Page<Goods> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Goods> findAllByOrderByPriceDesc(Pageable pageable);

    Page<Goods> findAllByOrderByIdDesc(Pageable pageable);

    Page<Goods> findAllByOrderById(Pageable pageable);
}
