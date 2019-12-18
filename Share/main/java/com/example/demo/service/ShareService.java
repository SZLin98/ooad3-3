package com.example.demo.service;


import com.example.demo.controllerInterface.GoodsController;
import com.example.demo.controllerInterface.OrderController;
import com.example.demo.dao.ShareDao;
import com.example.demo.domain.*;
import com.example.demo.domain.po.ShareRulePo;
import com.example.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.controllerInterface.UserInfoController;

import java.util.*;

/**
 * @author candy
 * @create 2019/12/12 22:05
 */
@Service
public class ShareService {


    @Autowired
    ShareDao shareDao;
    @Autowired
    UserInfoController userInfoController;
    @Autowired
    GoodsController goodsController;
    @Autowired
    OrderController orderController;

    private static final Logger logger = LoggerFactory.getLogger(ShareService.class);
    public Map<Integer,Integer> calculateRebate(Order order)
    {
        Integer beSharedId=order.getUserId();

        List<OrderItem> orderItemList = order.getOrderItemList();

        Map <Integer,Integer> rebateMap;
        //得到有效的BeSharedItem
        shareDao.getValidBeSharedItem(beSharedId,orderItemList);
        //根据返点方式增加shareItem的successNum
        shareDao.addShareSuccess(beSharedId);
        //计算每个分享者的返点
        rebateMap=shareDao.calculateRebate(orderItemList);
        //改变beShareItem的状态，从已分享0改为已返点1
        shareDao.alterStatues();
        return rebateMap;

    }

    public BeSharedItem createBeShareItem(BeSharedItem beSharedItem)
    {
        //判断goods是否在售
        if(!goodsController.isGoodsOnSale(beSharedItem.getGoodsId())) {
            logger.debug("该商品已下架");
            beSharedItem.setId(-1);
            return beSharedItem;
        }
        //判断sharer是否合法
        if(userInfoController.isValid(beSharedItem.getSharerId())==null){
            logger.debug("分享者不存在");
            beSharedItem.setId(-2);
            return beSharedItem;
        }
        try {
            return shareDao.createBeShareItem(beSharedItem);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public ShareRule getShareRule(Integer id) {
        ShareRule shareRule;
        try {
            shareRule=shareDao.getShareRule(id);
            //检查goods是否在售
            if (goodsController.isGoodsOnSale(shareRule.getGoodsId())) {
                return shareRule;
            }
            logger.debug("商品已下架");
            shareRule.setId(-1);
            return shareRule;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ShareRulePo createShareRule(ShareRulePo shareRule)
    {
        //检查goods是否在售
        if(goodsController.isGoodsOnSale(shareRule.getGoodsId()))
        {
            try {
                return shareDao.createShareRule(shareRule);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        logger.debug("该商品已下架");
        System.out.println("该商品已下架"+goodsController.isGoodsOnSale(shareRule.getGoodsId()));
        shareRule.setId(-1);
        return shareRule;
    }

    public Integer deShareRule(Integer id)
    {
        return shareDao.deShareRule(id);
    }

    public ShareRulePo alterShareRule(ShareRulePo shareRule,Integer id)
    {
        //检查goods是否在售
        if(goodsController.isGoodsOnSale(shareRule.getGoodsId()))
        {
            try {
                return shareDao.alterShareRule(shareRule, id);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        logger.debug("该商品已下架");
        System.out.println("该商品已下架"+goodsController.isGoodsOnSale(shareRule.getGoodsId()));
        shareRule.setId(-2);
        return shareRule;
    }

}
