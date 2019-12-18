package com.example.demo.mapper;

import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.ShareItem;
import com.example.demo.domain.ShareRule;
import com.example.demo.domain.po.ShareRulePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author candy
 */
@Mapper
public interface ShareMapper {
    /**
     * 根据goodsId返回分享规则
     * @param id
     * @return 分享规则
     */
    ShareRulePo getShareRuleByGoods(Integer id);

    /**
     * 根据id返回分享规则
     * @param id
     * @return
     */
    ShareRulePo getShareRuleById(Integer id);
    /**
     * create ShareRule
     * 新建分享规则
     * @param shareRule
     * @return
     */
    Integer createShareRule(ShareRulePo shareRule);

    /**
     * delete shareRule by id
     * 对shareRule进行逻辑删除，将is_deleted置1
     * @param id
     * @return shareRule
     */
    Integer deShareRule(Integer id);

    /**
     * 根据ID修改ShareRule
     * @param id
     * @param shareRule
     * @return
     */
    Integer alterShareRule(ShareRulePo shareRule, Integer id);

    /**
     * 新建用户的被分享表
     * @param beSharedItem
     * @return
     */
    Integer createBeShareItem(BeSharedItem beSharedItem);

    /**
     * 查看订单中的商品是否属于被分享的商品
     * 且是否在有效时间内
     * 返回BeSharedItem
     * @param userId
     * @param goodsId
     * @param sharerId
     * @return
     */
    ArrayList<BeSharedItem> checkBeSharedItem(@Param("beSharedId") Integer userId,
                                              @Param("goodsId") Integer goodsId,
                                              @Param("sharerId") Integer sharerId);

    /**
     * 改变BeSharedItem的状态，从已分享0改为已返点1
     *
     * @param beSharedItem
     * @return
     */
    Integer alterStatues(BeSharedItem beSharedItem);

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
     * @param shareItem
     * @return
     */
    Integer createShareItem(ShareItem shareItem);
    /**
     * 根据分享者id和商品id增加shareItem的successNum
     *
     * @param shareItem
     * @return
     */
    Integer addShareSuccess(ShareItem shareItem);

    /**
     * 获得一个OrderItem所有的BeSharedItem
     * 按创建时间birthTime排序
     * @param goodsId
     * @param beSharedUserId
     * @return
     */
    ArrayList<BeSharedItem> getSameBeShareItem(@Param("goodsId") Integer goodsId,@Param("beSharedId") Integer beSharedUserId);



}
