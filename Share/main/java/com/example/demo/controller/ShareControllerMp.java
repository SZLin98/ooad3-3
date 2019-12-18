package com.example.demo.controller;


import com.example.demo.controllerInterface.GoodsController;
import com.example.demo.controllerInterface.LogController;
import com.example.demo.controllerInterface.OrderController;
import com.example.demo.controllerInterface.ShareController;
import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.*;
import com.example.demo.domain.po.ShareRulePo;
import com.example.demo.service.ShareService;
import com.example.demo.util.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;


/**
 * @author candy
 *
 */
@RestController
public class ShareControllerMp implements ShareController {
    @Autowired
    private ShareService shareService;
    @Autowired
    private LogController logController;
    private static final Logger logger = LoggerFactory.getLogger(ShareControllerMp.class);

    /**
     *计算返点
     * @param order
     * @return  Integer
     */
    @Override
    public Object calculateRebate(Order order)
    {
        Map<Integer,Integer> rebateMap;

        rebateMap=shareService.calculateRebate(order);
        if(rebateMap!=null){
            Object rebateObj = ResponseUtil.ok(rebateMap);
            logger.debug("calculateRebate：" + rebateObj);
            return  rebateObj;
        }
        else{
            Object failObj = ResponseUtil.serious();
            logger.debug("计算返点失败"+failObj);
            return failObj;
        }

    }

    /**
     * 获取分享规则
     * @param id
     * @return
     */
    @Override
    public Object list(HttpServletRequest request, Integer id) throws UnknownHostException {

        Log log=new Log();
        InetAddress address = Inet4Address.getLocalHost();

        Integer userId = GetUser.getUserId(request);
        userId=1;
        if(userId==null)
        {
            Object failObj = ResponseUtil.unlogin();
            logger.debug("获取分享规则失败"+failObj);
            return failObj;
        }

        ShareRule shareRule=shareService.getShareRule(id);

        log.setAdminId(userId);
        log.setIp(address.getHostAddress());
        log.setType(0);
        log.setAction("获取分享规则");

        if(shareRule.getId()>0) {
            Object shareObj = ResponseUtil.ok(shareRule);
            logger.debug("getShareRule的返回值：" + shareObj);
            log.setActionId(shareRule.getId());
            log.setStatusCode(1);
            logController.writeLog(log);
            return shareObj;
        }
        else if(shareRule.getId()==-1) {
            Object shareObj = ResponseUtil.fail(402,"商品已下架");
            logger.debug("getShareRule的返回值：" + shareObj);
            log.setActionId(null);
            log.setStatusCode(0);
            logController.writeLog(log);
            return shareObj;
        }
        else
        {
            Object failObj = ResponseUtil.serious();
            logger.debug("获取分享规则失败"+failObj);
            log.setActionId(null);
            log.setStatusCode(0);
            logController.writeLog(log);
            return failObj;
        }

    }

    /**
     * 新建分享规则
     * @param shareRule
     * @return
     */
    @Override
    public Object create(HttpServletRequest request, ShareRulePo shareRule) throws UnknownHostException {

        Log log=new Log();
        InetAddress address = Inet4Address.getLocalHost();

        Integer userId=GetUser.getUserId(request);
        userId=1;
        if(userId==null){
            Object failObj=ResponseUtil.unlogin();
            logger.debug("新建分享规则失败"+failObj);
            return failObj;
        }
        if(shareRule.getGoodsId()==null){
            Object failObj=ResponseUtil.fail(402,"无商品");
            logger.debug("新建分享规则失败"+failObj);
            return failObj;
        }
        if(shareRule.getShareLevelStrategy()==null){
            Object failObj=ResponseUtil.fail(402,"分享级别策略为空");
            logger.debug("新建分享规则失败"+failObj);
            return failObj;
        }

        System.out.println(shareRule.toString());
        log.setAdminId(userId);
        log.setIp(address.getHostAddress());
        log.setType(1);
        log.setAction("新建分享规则");

        ShareRulePo newShareRule=shareService.createShareRule(shareRule);
        if(newShareRule.getId()>0) {
            Object newShareObj = ResponseUtil.ok(newShareRule);
            logger.debug("createShareRule的返回值:" + newShareObj);
            log.setActionId(shareRule.getId());
            log.setStatusCode(1);
            logController.writeLog(log);
            return newShareObj;
            }
        else if(newShareRule.getId()==-1){
            Object failObj=ResponseUtil.fail(507,"该商品已下架");
            logger.debug("新建分享规则失败"+failObj);
            log.setActionId(shareRule.getId());
            log.setStatusCode(0);
            logController.writeLog(log);
            return failObj;
            }
        else if(newShareRule.getId()==-2){
            Object failObj=ResponseUtil.fail(507,"一个商品只能有一个分享规则");
            logger.debug("新建分享规则失败"+failObj);
            log.setActionId(shareRule.getId());
            log.setStatusCode(0);
            logController.writeLog(log);
            return failObj;
        }
        else if(newShareRule.getId()==-3){
            Object failObj=ResponseUtil.fail(502,"操作数据库失败");
            logger.debug("新建分享规则失败"+failObj);
            log.setActionId(shareRule.getId());
            log.setStatusCode(0);
            logController.writeLog(log);
            return failObj;
        }
        else {
            Object failObj=ResponseUtil.serious();
            logger.debug("新建分享规则失败"+failObj);
            log.setActionId(shareRule.getId());
            log.setStatusCode(0);
            logController.writeLog(log);
            return failObj;
        }
    }
    /**
     * 根据id删除分享规则
     * @param id
     * @return
     */
    @Override
    public Object delete(HttpServletRequest request,Integer id) throws UnknownHostException {

        Log log=new Log();
        InetAddress address = Inet4Address.getLocalHost();

        Integer userId = GetUser.getUserId(request);
        userId=1;
        if(userId==null)
        {
            Object failObj = ResponseUtil.unlogin();
            logger.debug("删除分享规则失败"+failObj);
            return failObj;
        }
        log.setAdminId(userId);
        log.setIp(address.getHostAddress());
        log.setType(3);
        log.setAction("删除分享规则");

        Integer deShare = shareService.deShareRule(id);
        Object deShareObj;
        if(deShare==1) {
            deShareObj=ResponseUtil.ok();
            logger.debug("删除分享规则成功");
            log.setActionId(id);
            log.setStatusCode(1);
            logController.writeLog(log);
        }
        else if(deShare==-1){
            deShareObj=ResponseUtil.fail(402,"该分享规则不存在");
            logger.debug("删除分享规则失败,该分享规则不存在");
            log.setActionId(id);
            log.setStatusCode(0);
            logController.writeLog(log);
        }
        else {
            deShareObj=ResponseUtil.serious();
            logger.debug("删除分享规则失败");
            log.setActionId(id);
            log.setStatusCode(0);
            logController.writeLog(log);
        }
        return deShareObj;
    }

    /**
     * 更新分享规则
     * @param shareRule
     * @return
     */
    @Override
    public Object update(HttpServletRequest request,ShareRulePo shareRule, Integer id) throws UnknownHostException {

        Log log=new Log();
        InetAddress address = Inet4Address.getLocalHost();

        Integer userId = GetUser.getUserId(request);
        userId=1;
        if(userId==null)
        {
            Object failObj = ResponseUtil.unlogin();
            logger.debug("更新分享规则失败"+failObj);
            return failObj;
        }
        if(shareRule.getGoodsId()==null && shareRule.getShareLevelStrategy()==null){
            Object failObj=ResponseUtil.fail(402,"修改内容为空");
            logger.debug("更新分享规则失败"+failObj);
            return failObj;
        }

        log.setAdminId(userId);
        log.setIp(address.getHostAddress());
        log.setType(2);
        log.setAction("更新分享规则");

        ShareRulePo newShareRule=shareService.alterShareRule(shareRule,id);
        if (newShareRule.getId()>0) {
            Object newShareObj = ResponseUtil.ok(newShareRule);
            logger.debug("修改分享规则的返回值" + newShareObj);
            log.setActionId(id);
            log.setStatusCode(1);
            logController.writeLog(log);
            return newShareObj;
        }
        else if(newShareRule.getId()==-1){
            Object failObj = ResponseUtil.fail(402,"该分享规则不存在");
            logger.debug("修改分享规则失败"+failObj);
            log.setActionId(id);
            log.setStatusCode(0);
            logController.writeLog(log);
            return  failObj;
        }
        else if(newShareRule.getId()==-2){
            Object failObj = ResponseUtil.fail(402,"该商品已下架");
            logger.debug("修改分享规则失败"+failObj);
            log.setActionId(id);
            log.setStatusCode(0);
            logController.writeLog(log);
            return  failObj;
        }
        else if(newShareRule.getId()==-3){
            Object failObj = ResponseUtil.fail(505,"操作数据库失败");
            logger.debug("修改分享规则失败"+failObj);
            log.setActionId(id);
            log.setStatusCode(0);
            logController.writeLog(log);
            return  failObj;
        }
        else
        {
            Object failObj = ResponseUtil.serious();
            logger.debug("修改分享规则失败"+failObj);
            log.setActionId(id);
            log.setStatusCode(0);
            logController.writeLog(log);
            return  failObj;
        }
    }
    /**
     * 增加某个用户的被分享表
     * @param beSharedItem
     * @return
     */
    @Override
    public Object createSharedItems(HttpServletRequest request,BeSharedItem beSharedItem) {

        Integer userId = GetUser.getUserId(request);
        userId=1;
        if(userId==null)
        {
            Object failObj = ResponseUtil.unlogin();
            logger.debug("新建用户的被分享条目失败"+failObj);
            return failObj;
        }
        if(beSharedItem.getGoodsId()==null){
            Object failObj=ResponseUtil.fail(402,"无商品");
            logger.debug("新建用户的被分享条目失败"+failObj);
            return failObj;
        }
        if(beSharedItem.getSharerId()==null){
            Object failObj=ResponseUtil.fail(402,"无分享人");
            logger.debug("新建用户的被分享条目失败"+failObj);
            return failObj;
        }
        if(beSharedItem.getBeSharedUserId()==null){
            Object failObj=ResponseUtil.fail(402,"无被分享人");
            logger.debug("新建用户的被分享条目失败"+failObj);
            return failObj;
        }
        BeSharedItem newBeSharedItem = shareService.createBeShareItem(beSharedItem);
        if (newBeSharedItem .getId()>0) {
            Object newBeShareObj = ResponseUtil.ok(newBeSharedItem);
            logger.debug("createSharedItems的返回值：" + newBeShareObj);
            return newBeShareObj;
        }
        else if(newBeSharedItem .getId()==-1){
            Object failObj = ResponseUtil.fail(402,"该商品已下架");
            logger.debug("新建用户的被分享条目失败：" + failObj);
            return failObj;
        }
        else if(newBeSharedItem .getId()==-2){
            Object failObj = ResponseUtil.fail(402,"分享者不存在");
            logger.debug("新建用户的被分享条目失败：" + failObj);
            return failObj;
        }
        else if(newBeSharedItem .getId()==-3){
            Object failObj = ResponseUtil.fail(505,"操作数据库失败");
            logger.debug("新建用户的被分享条目失败：" + failObj);
            return failObj;
        }
        else if(newBeSharedItem .getId()==-4){
            Object failObj = ResponseUtil.fail(402,"相同的被分享条目只能创建一次");
            logger.debug("新建用户的被分享条目失败：" + failObj);
            return failObj;
        }
        else {
            Object failObj = ResponseUtil.serious();
            logger.debug("新建用户的被分享条目失败：" + failObj);
            return failObj;
        }

    }

}
