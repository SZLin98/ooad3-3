package com.example.demo.dao;

import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.ShareItem;
import com.example.demo.domain.ShareRule;
import com.example.demo.domain.po.ShareRulePo;
import com.example.demo.mapper.ShareMapper;
import com.example.demo.service.ShareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author candy
 */

@Repository
public class ShareDao {
    private static final Logger logger = LoggerFactory.getLogger(ShareDao.class);

    @Resource
    private ShareMapper shareMapper;

    /**
     * 查看订单中的商品是否属于被分享的商品，如果属于，返回BeSharedItem的ID
     * @param beSharedId
     * @param goodsId
     * @return beSharedID
     */
    public Integer checkBeSharedItem(Integer beSharedId, Integer goodsId) {

        Integer BeSharedId=shareMapper.checkBeSharedItem(beSharedId,goodsId);
        return BeSharedId;
    }
    public ShareRule getShareRule(Integer goodsId)
    {
        //通过goodsId查看分享规则
        return shareMapper.getShareRule(goodsId);
    }

    public ShareRule createShareRule(ShareRule shareRule)
    {
        return shareMapper.createShareRule(shareRule);
    }

    public boolean deShareRule(Integer id)
    {
        return shareMapper.deShareRule(id);
    }

    public ShareRule alterShareRule(ShareRule shareRule, Integer id)
    {
        return shareMapper.alterShareRule(shareRule,id);
    }

    public BeSharedItem createBeShareItem(BeSharedItem beSharedItem)
    {
        return shareMapper.createBeShareItem(beSharedItem);
    }

    public BeSharedItem alterStatues(Integer beSharedItemId) {
        return shareMapper.alterStatues(beSharedItemId);
    }
    public ShareItem checkSharedItem(Integer sharerId, Integer goodsId) {
        return shareMapper.checkSharedItem(sharerId,goodsId);
    }
    public ShareItem addShareSuccess(Integer sharerId, Integer goodsId) {
        return shareMapper.addShareSuccess(sharerId,goodsId);
    }
    public ShareItem createShareItem(Integer sharerId, Integer goodsId)
    {
        return shareMapper.addShareItem(sharerId,goodsId);
    }

}
