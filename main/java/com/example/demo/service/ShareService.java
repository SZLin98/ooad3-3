package com.example.demo.service;


import com.example.demo.dao.ShareDao;
import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.ShareItem;
import com.example.demo.domain.ShareRule;
import com.example.demo.domain.po.ShareRulePo;
import com.google.gson.*;
import io.swagger.models.auth.In;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author candy
 * @create 2019/12/12 22:05
 */
@Service
public class ShareService {


    @Autowired
    ShareDao shareDao;

    private ArrayList<Integer> beSharedItemIdList = new ArrayList<>();
    private ArrayList<BeSharedItem> beSharedItemArrayList = new ArrayList<>();
    private ArrayList<ShareItem> shareItemArrayList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(ShareService.class);
    /**
     * 新建用户的被分享表，相同的被分享者和goods只记录一次
     * @param beSharedItem
     * @return
     */
    public BeSharedItem createBeShareItem(BeSharedItem beSharedItem)
    {
        if(shareDao.checkBeSharedItem(beSharedItem.getBeSharedUserId(),beSharedItem.getGoodsId())!=null)
        {
            return shareDao.createBeShareItem(beSharedItem);
        }
        else
        {
            return null;
        }
    }
    /**
     * 计算返点
     * 根据shareItem里的successNum对应到相应shareRule的strategyLevel
     *
     */
    public Map calculateRebate()
    {
        ArrayList<OrderItem>OrderItemList=new ArrayList<>();
        ArrayList<Integer>LowerBound=new ArrayList<>();
        ArrayList<Integer>UpperBound=new ArrayList<>();
        ArrayList<BigDecimal>Rate=new ArrayList<>();
        Integer successNum,
                rebate,
                lowerBound,
                upperBound;
        BigDecimal rate;
        BigDecimal price;

        Map <Integer,Integer>rebateMap=new HashMap();
        Map <Integer, BigDecimal>goodPrice=new HashMap();

        //记录goodsID和price的对应关系
        for(OrderItem orderItem:OrderItemList)
        {
            goodPrice.put(orderItem.getGoodsId(),orderItem.getDealPrice());
        }
        //根据shareItem得到相应分享规则
        for(ShareItem shareItem:shareItemArrayList)
        {
            //得到shareRule
            ShareRule shareRule=shareDao.getShareRule(shareItem.getGoodsId());

            String shareLevelStrategy = shareRule.getShareLevelStrategy();
            JsonObject jObject = new JsonParser().parse(shareLevelStrategy).getAsJsonObject();
            JsonArray array = jObject.get("strategy").getAsJsonArray();
            for(JsonElement jsonElement : array){
                JsonObject jo = jsonElement.getAsJsonObject();

                lowerBound = jo.get("lowerbound").getAsInt();
                upperBound = jo.get("upperbound").getAsInt();
                rate = new BigDecimal(jo.get("rate").getAsString());

                logger.debug("lowerbound"+lowerBound);
                logger.debug("upperbound"+upperBound);
                logger.debug("rate"+rate);

                LowerBound.add(lowerBound);
                UpperBound.add(upperBound);
                Rate.add(rate);
            }


            //得到该条分享者分享记录的成功次数
            successNum=shareItem.getSuccessNum();
            //得到该条分享记录的商品价格
            price=new BigDecimal(goodPrice.get(shareItem.getGoodsId()).toString());
            //如果成功次数小于规则所规定策略的最低值，无返点
            if(successNum<LowerBound.get(0))
            {
                return null;
            }

            else
            {
                int n=LowerBound.size();
                int level=0;
                //找到分享记录所对应的区间
                for(int i=0; i<n;i++) {
                    if(successNum>UpperBound.get(i)) {
                        level++;
                        continue;
                    }
                    else {
                        break;
                    }
                }
                if(level==n) {
                    level=n-1;
                }

                //检查之前是否有该分享者的返点记录
                //检查rebateMap集合中有没有包含Key为userId的元素，如果有则返回true，否则返回false。
                boolean isExist=rebateMap.containsKey(shareItem.getUserId());
                //如果直接没有记录过这个分享者
                if(!isExist) {
                    //计算返点
                    rebate=price.multiply(Rate.get(level)).intValue();
                    rebateMap.put(shareItem.getUserId(),rebate);
                }
                //如果之前记录过，则用该条记录的返点加上之前的返点
                else {
                    Integer curRebate=rebateMap.get(shareItem.getUserId());
                    rebate=curRebate+price.multiply(Rate.get(level)).intValue();
                    rebateMap.put(shareItem.getUserId(),rebate);
                }
            }

        }
        return rebateMap;
    }
    /**
     * 用户下单后七天内未退货,改变beShareItem的状态
     *
     */
    public ArrayList<BeSharedItem> alterStatues(Integer userId,Object orderItems)
    {
        ArrayList<OrderItem>OrderItemList=new ArrayList<>();

        for(OrderItem orderItem:OrderItemList)
        {
            //检查商品是否属于该用户被分享的商品
            Integer sharedId=shareDao.checkBeSharedItem(userId, orderItem.getGoodsId());
            if(sharedId!=null)
            {
                beSharedItemIdList.add(sharedId);
            }
        }

        for(Integer beSharedItemId:beSharedItemIdList)
        {
            this.beSharedItemArrayList.add(shareDao.alterStatues(beSharedItemId));
        }
        return beSharedItemArrayList;
    }

    /**
     * 当BeSharedItem的状态改为已返点时,给shareItem的successNum加1
     * @return
     */
    public ArrayList<ShareItem> addShareSuccess()
    {
        Integer sharerId, goodsId;
        for(BeSharedItem beSharedItem:beSharedItemArrayList)
        {
            sharerId=beSharedItem.getSharerId();
            goodsId=beSharedItem.getGoodsId();
            if(shareDao.checkSharedItem(sharerId,goodsId)!=null)
            {
                shareItemArrayList.add(shareDao.addShareSuccess(sharerId,goodsId));
            }
            else
            {
                shareItemArrayList.add((shareDao.createShareItem(sharerId,goodsId)));
            }
        }
        return shareItemArrayList;
    }
    public ShareItem addShareItem()
    {
        return null;
    }
    public ShareRule getShareRule(Integer goodsId)
    {
        return shareDao.getShareRule(goodsId);
    }

    public ShareRule createShareRule(ShareRule shareRule)
    {
        return shareDao.createShareRule(shareRule);
    }

    public boolean deShareRule(Integer id)
    {
        return shareDao.deShareRule(id);
    }

    public ShareRule alterShareRule(ShareRule shareRule,Integer id)
    {
        return shareDao.alterShareRule(shareRule,id);
    }

}
