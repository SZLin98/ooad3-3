package com.example.demo.dao;

import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.ShareItem;
import com.example.demo.domain.ShareRule;
import com.example.demo.domain.po.ShareRulePo;
import com.example.demo.mapper.ShareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author candy
 */

@Repository
public class ShareDao {
    private static final Logger logger = LoggerFactory.getLogger(ShareDao.class);

    @Resource
    private ShareMapper shareMapper;

    /**
     * 记录所有有效BeSharedItem
     */
    private ArrayList<BeSharedItem> validBeSharedItem = new ArrayList<>();
    /**
     * 记录该订单中所有有效的分享商品
     */
    private ArrayList<Integer> validSharedGoods = new ArrayList<>();
    /**
     * 记录所有增加了successNum的SharedItem
     */
    private ArrayList<ShareItem> shareItemArrayList = new ArrayList<>();

    /**
     * 计算用户应得的返点
     * @param orderItemList 订单条目列表
     * @return Map 每个分享者应该得到多少返点
     */
    public Map<Integer,Integer> calculateRebate(List<OrderItem> orderItemList){


        Integer rebate;
        BigDecimal price;

        HashMap<Integer,Integer> rebateMap=new HashMap<>();
        HashMap<Integer, BigDecimal> goodPrice=new HashMap<>(validSharedGoods.size());

        //记录goodsID和price的对应关系
        for(OrderItem orderItem: orderItemList )
        {
            goodPrice.put(orderItem.getGoodsId(),orderItem.getDealPrice());
        }
        //根据shareItem得到相应分享规则
        for(ShareItem shareItem:shareItemArrayList)
        {
            //得到shareRule
            ShareRule shareRule=new ShareRule(shareMapper.getShareRuleByGoods(shareItem.getGoodsId()));

            //得到该条分享记录的商品价格
            price=new BigDecimal(goodPrice.get(shareItem.getGoodsId()).toString());

            //检查之前是否有该分享者的返点记录
            //检查rebateMap集合中有没有包含Key为userId的元素，如果有则返回true，否则返回false。
            boolean isExist=rebateMap.containsKey(shareItem.getUserId());
            //如果之前没有记录过这个分享者
            if(!isExist) {

                rebate=shareRule.calculateRebate(shareItem,price);
                if(rebate==null){
                    continue;
                }
                rebateMap.put(shareItem.getUserId(), rebate);
            }
            //如果之前记录过，则用该条记录的返点加上之前的返点
            else {
                Integer curRebate=rebateMap.get(shareItem.getUserId());

                rebate=shareRule.calculateRebate(shareItem,price);
                if(rebate==null){
                    continue;
                }
                rebate+=+curRebate;
                rebateMap.put(shareItem.getUserId(),rebate);
                }
            }

        return rebateMap;
    }

    /**
     * 查看订单中的OrderItem是否满足条件
     * 被分享者在有效时间内下单
     * 且下单后七天内未退货
     * 得到有效BeSharedItem
     * @param orderItemList 订单条目列表
     * @param userId 购买者
     */
    public void getValidBeSharedItem(Integer userId,List<OrderItem> orderItemList)
    {
        for(OrderItem orderItem:orderItemList)
        {
            //检查该订单中的商品是否属于该用户被分享的商品

             //记录所有BeSharedItem
            ArrayList<BeSharedItem> beSharedItemList = shareMapper.checkBeSharedItem(userId, orderItem.getGoodsId(), null);
            if(beSharedItemList !=null) {
                for(BeSharedItem beSharedItem: beSharedItemList) {
                    //检查下单时间是否在有效时间内，即成功分享的7天之内
                    LocalDateTime beSharedCreate = beSharedItem.getBirthTime();
                    LocalDateTime endTime = beSharedCreate.plusDays(7);
                    if (orderItem.getGmtCreate().isAfter(endTime)) {
                        validBeSharedItem.add(beSharedItem);
                        //得到有效的被分享商品
                        if(!validSharedGoods.contains(beSharedItem.getGoodsId()))
                        {
                            validSharedGoods.add(beSharedItem.getGoodsId());
                        }
                    }
                }
            }
        }
    }
    /**
     * 增加shareItem的successNum
     * @param beSharedUserId 被分享者id
     */
    public void addShareSuccess(Integer beSharedUserId) {
        //第一种情况，返给在有效时间内最早分享成功的用户
        //第二种情况，平分给所有有效时间内分享成功的用户
        Integer successNum=100;

        ArrayList<BeSharedItem> beSharedItems;
        ShareItem shareItem;
        //得到有效分享商品的所有有效BeSharedItem
        for(Integer goodsId:validSharedGoods)
        {
            beSharedItems=shareMapper.getSameBeShareItem(beSharedUserId,goodsId);
            //检查是否有效
            for(BeSharedItem beSharedItem:beSharedItems)
            {
                //如果有效BeSharedItem列表中不存在该对象，则删除
                if(!validBeSharedItem.contains(beSharedItem))
                {
                    beSharedItems.remove(beSharedItem);
                }
            }

            ShareRule shareRule=new ShareRule(shareMapper.getShareRuleByGoods(goodsId));
            if(shareRule.getShareType()==1)
            {//第一种情况，平分
                // 把successNum根据分享人数量平分
                int n=beSharedItems.size();
                if(n<=100) {successNum/=n;}
                else {
                    //若分享人数量大于100，则只取前100人
                    successNum/=100;
                    n=100;}
                for(int i=0;i<n;i++)
                {//为每个分享者增加successNum
                    shareItem=shareMapper.checkSharedItem(beSharedItems.get(i).getSharerId(), goodsId);
                    if (shareItem == null) {
                        if(shareMapper.createShareItem(beSharedItems.get(i).setShareItem(successNum))==0){
                            logger.debug("创建shareItem失败");
                        }
                    }
                    else{
                        if(shareMapper.addShareSuccess(beSharedItems.get(i).setShareItem(successNum))==0){
                            logger.debug("更新shareItem失败");
                        }
                    }
                    shareItemArrayList.add(shareItem);
                }
            }
            else
            {//第二种情况，只记第一个
                if(shareMapper.checkSharedItem(beSharedItems.get(0).getSharerId(),goodsId) != null) {
                    shareMapper.addShareSuccess(beSharedItems.get(0).setShareItem(successNum));
                }
                else{
                    shareMapper.createShareItem(beSharedItems.get(0).setShareItem(successNum));
                }
            }
        }
    }

    /**
     * 改变beShareItem的状态为已返点
     */
    public void alterStatues() {
        int alterNum=0;
        for(BeSharedItem beSharedItem:validBeSharedItem)
        {
            beSharedItem.alterStatus();
            if(shareMapper.alterStatues(beSharedItem)!=null)
            {
                alterNum++;
            }
        }
        if(alterNum<validBeSharedItem.size()) {
            logger.debug("改变beShareItem的状态失败");
        }

   }

    /**
     * 通过goodsId查看分享规则
     * @param id 商品id
     * @return 返回shareRule
     */
    public ShareRule getShareRule(Integer id)
    {
        ShareRulePo shareRulePo=shareMapper.getShareRuleByGoods(id);
        return new ShareRule(shareRulePo);

    }

    /**
     * 新建分享规则
     * @param shareRule 分享规则
     * @return 分享规则
     */
    public ShareRulePo createShareRule(ShareRulePo shareRule)
    {
        //一个商品只能有一个分享规则
        if(shareMapper.getShareRuleByGoods(shareRule.getGoodsId())==null)
        {
            if(shareMapper.createShareRule(shareRule)!=0)
            {
                return shareRule;
            }
            shareRule.setId(-3);
            logger.debug("创建分享规则失败，操作数据库失败");
            return shareRule;
        }
        shareRule.setId(-2);
        logger.debug("一个商品只能有一个分享规则");
        return shareRule;
    }

    public Integer deShareRule(Integer id)
    {
        //查看shareRule是否存在
        if(shareMapper.getShareRuleById(id)!=null){
            return shareMapper.deShareRule(id);
        }
        logger.debug("该分享规则不存在");
        return -1;
    }

    public ShareRulePo alterShareRule(ShareRulePo shareRule, Integer id)
    {
        if(shareMapper.getShareRuleById(id)!=null) {
            if(shareMapper.alterShareRule(shareRule,id)!=0){
                return shareRule;
            }
            else{
                shareRule.setId(-3);
                return shareRule;
            }
        }
        else{
            logger.debug("该分享规则不存在");
            shareRule.setId(-1);
            return shareRule;
        }
    }

    public BeSharedItem createBeShareItem(BeSharedItem beSharedItem) {
        if (shareMapper.checkBeSharedItem(beSharedItem.getBeSharedUserId(),
                beSharedItem.getSharerId(),
                beSharedItem.getGoodsId()) == null) {
            if (shareMapper.createBeShareItem(beSharedItem) != null) {
                return beSharedItem;
            } else {
                beSharedItem.setId(-3);
                logger.debug("操作数据库失败");
                return beSharedItem;
            }
        } else {
                beSharedItem.setId(-4);
                logger.debug("相同的被分享条目只能创建一次");
                return beSharedItem;
        }
    }
}
