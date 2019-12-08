package ooad33.order.controller;

import ooad33.order.domain.Product;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lsz
 * @create 2019/12/8 9:58
 */
@RestController
public class FalseGoodsController implements GoodsController {


    @Override
    public Product findProductById(Integer productId) {
        Product product=new Product();
        product.setId(productId);
        return product;
    }
}
