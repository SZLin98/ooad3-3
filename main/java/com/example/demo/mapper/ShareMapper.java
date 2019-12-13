package com.example.demo.mapper;

import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.ShareItem;
import com.example.demo.domain.ShareRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author candy
 */
@Mapper
public interface ShareMapper {
    /**
     * 返回分享规则
     * @param goodsId
     * @return 分享规则
     */
    ShareRule getShareRule(Integer goodsId);

    /**
     * create ShareRule
     *
     * @param shareRule
     * @return
     */
    ShareRule createShareRule(ShareRule shareRule);

    /**
     * delete shareRule by id
     *
     * @param id
     * @return shareRule
     */
    boolean deShareRule(Integer id);

    /**
     * 根据ID修改ShareRule
     * @param id
     * @param shareRule
     * @return
     */
    ShareRule alterShareRule(ShareRule shareRule, Integer id);

    /**
     * 新建用户的被分享表
     * @param beSharedItem
     * @return
     */
    BeSharedItem createBeShareItem(BeSharedItem beSharedItem);

    /**
     * 查看订单中的商品是否属于被分享的商品，如果属于，返回BeSharedItem的ID
     *
     * @param beSharedId
     * @param goodsId
     * @return
     */
    Integer checkBeSharedItem(@Param("beSharedId") Integer beSharedId, @Param("goodsId") Integer goodsId);

    /**
     * 改变BeSharedItem的状态，从已分享0改为已返点1
     *
     * @param beSharedItemId
     * @return
     */
    BeSharedItem alterStatues(Integer beSharedItemId);

    /**
     * 根据分享者id和商品id是否有相应的shareItem
     *
     * @param sharerId
     * @param goodsId
     * @return
     */
    ShareItem checkSharedItem(@Param("sharerId") Integer sharerId, @Param("goodsId") Integer goodsId);

    /**
     * 增加shareItem
     * @param sharerId
     * @param goodsId
     * @return
     */
    ShareItem addShareItem(@Param("sharerId") Integer sharerId, @Param("goodsId") Integer goodsId);
    /**
     * 根据分享者id和商品id增加shareItem的successNum
     *
     * @param sharerId
     * @param goodsId
     * @return
     */
    ShareItem addShareSuccess(@Param("shareId") Integer sharerId, @Param("goodsId") Integer goodsId);


}
