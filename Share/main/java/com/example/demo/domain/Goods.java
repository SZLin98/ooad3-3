package com.example.demo.domain;

import com.example.demo.domain.po.BrandPo;
import com.example.demo.domain.po.GoodsCategoryPo;
import com.example.demo.domain.po.GoodsPo;
import com.example.demo.domain.po.ProductPo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:商品对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Goods extends GoodsPo {
    private BrandPo brandPo;
    private GoodsCategoryPo goodsCategoryPo;
    private List<ProductPo> productPoList;
    private GroupOnRule groupOnRule;
    private ShareRule shareRule;
    private PresaleRule presaleRule;
}
