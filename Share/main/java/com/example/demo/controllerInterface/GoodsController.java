package com.example.demo.controllerInterface;


import com.example.demo.domain.Brand;
import com.example.demo.domain.Goods;
import com.example.demo.domain.GoodsCategory;
import com.example.demo.domain.Product;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 模块标准组
 * @Description:商品模块外部及内部api
 * @create 2019/12/3 18:30
 */

@RestController
@RequestMapping("/goodsService")
public interface GoodsController {

    /**
     * 判断商品是否在售 - 内部
     * @param id
     * @return
     */
    @GetMapping("/goods/{id}/isOnSale")
    public boolean isGoodsOnSale(@PathVariable Integer id);

}
