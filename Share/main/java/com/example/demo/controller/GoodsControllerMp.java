package com.example.demo.controller;

import com.example.demo.controllerInterface.GoodsController;
import com.example.demo.domain.Brand;
import com.example.demo.domain.Goods;
import com.example.demo.domain.GoodsCategory;
import com.example.demo.domain.Product;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author candy
 */
@RestController
public class GoodsControllerMp implements GoodsController {

    @Override
    public boolean isGoodsOnSale(Integer id) {
        return true;
    }
}
