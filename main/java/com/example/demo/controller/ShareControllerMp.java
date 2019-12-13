package com.example.demo.controller;


import com.example.demo.controllerInterface.GoodsController;
import com.example.demo.controllerInterface.OrderController;
import com.example.demo.controllerInterface.ShareController;
import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.*;
import com.example.demo.service.ShareService;
import com.example.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author candy
 *
 */
@RestController
@RequestMapping("/")
public class ShareControllerMp implements ShareController {
    @Autowired
    private ShareService shareService;
    @Autowired
    private OrderController orderController;
    @Autowired
    GoodsController goodsController;


    private static final Logger logger = LoggerFactory.getLogger(ShareControllerMp.class);

    /**
     *计算返点
     * @param order
     * @return  Integer
     */
    @Override
    public Object calculateRebate(Order order)
    {
       // List<OrderItem> orderItems;
        Object orderItems = orderController.userDetail(null,order.getId());
        shareService.alterStatues(order.getUserId(),orderItems);
        shareService.addShareSuccess();
        shareService.calculateRebate();
        return null;
    }

    /**
        获取分享规则
     */
    @Override
    public Object list(Integer goodsId) {
        ShareRule shareRule=shareService.getShareRule(goodsId);
        if(shareRule!=null) {
            Object shareObj = ResponseUtil.ok(shareRule);
            logger.debug("getShareRule的返回值：" + shareObj);
            return shareObj;
        }
        else
        {
            Object failObj = ResponseUtil.fail();
            return failObj;
        }
    }

    /**
     * 新建分享规则
     * @param shareRule
     * @return
     */
    @Override
    public Object create(ShareRule shareRule) {

        if(goodsController.isGoodsOnSale(shareRule.getGoodsId())!=null)
        {
            ShareRule newShareRule=shareService.createShareRule(shareRule);
            Object newShareObj = ResponseUtil.ok(newShareRule);
            logger.debug("新建ShareRule的返回值:" + newShareObj);
            return newShareObj;
        }
        else
        {
            Object failObj=ResponseUtil.fail();
            return failObj;
        }
    }
    /**
     * 根据id删除分享规则
     * @param id
     * @return
     */
    @Override
    public Object delete(Integer id) {
        boolean deShare = shareService.deShareRule(id);

        Object deShareObj;
        if(deShare) {
            deShareObj=ResponseUtil.ok();
            logger.debug("删除分享规则成功");
        }
        else {
            deShareObj=ResponseUtil.fail();
            logger.debug("删除分享规则失败");
        }
        return deShareObj;
    }

    /**
     * 更新分享规则
     * @param shareRule
     * @return
     */
    @Override
    public Object update(ShareRule shareRule, Integer id) {
        ShareRule newShareRule=shareService.alterShareRule(shareRule,id);
        if (newShareRule!=null) {
            Object newShareObj = ResponseUtil.ok(newShareRule);
            logger.debug("修改分享规则的返回值" + newShareObj);
            return newShareObj;
        }
        else
        {
            Object failObj = ResponseUtil.fail();
            return  failObj;
        }
    }
    /**
     * 增加某个用户的被分享表
     * @param beSharedItem
     * @return
     */
    @Override
    public Object createSharedItems(BeSharedItem beSharedItem) {
        BeSharedItem newBeSharedItem = shareService.createBeShareItem(beSharedItem);
        if(newBeSharedItem!=null)
        {
            Object newBeShareObj=ResponseUtil.ok(newBeSharedItem);
            logger.debug("createSharedItems的返回值："+newBeShareObj);
            return newBeShareObj;
        }
        else {
            return null;
        }
    }



}
