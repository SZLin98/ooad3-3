package com.example.demo.domain;


import com.example.demo.domain.po.ShareRulePo;
import com.google.gson.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:分享规则对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ShareRule extends ShareRulePo {
    @Getter
    @Setter
    private static class Strategy{
        private Integer lowerBound;
        private Integer upperBound;
        private BigDecimal discountRate;
    }
    private List<Strategy> strategyList;
    private Integer shareType;

    private static final Logger logger = LoggerFactory.getLogger(ShareRule.class);
    public ShareRule() { }
    public ShareRule(ShareRulePo shareRulePo)
    {
        String shareLevelStrategy= shareRulePo.getShareLevelStrategy();

        Strategy strategy= new Strategy();
        List<Strategy>strategies=new ArrayList<>();
        JsonObject jObject = new JsonParser().parse(shareLevelStrategy).getAsJsonObject();
        JsonArray array = jObject.get("strategy").getAsJsonArray();

        for(JsonElement jsonElement : array){

            JsonObject jo = jsonElement.getAsJsonObject();
            strategy.lowerBound = jo.get("lowerbound").getAsInt();
            strategy.upperBound = jo.get("upperbound").getAsInt();
            strategy.discountRate = new BigDecimal(jo.get("rate").getAsString());

            strategies.add(strategy);
        }

        this.setShareType(jObject.get("type").getAsInt());
        this.setStrategyList(strategies);
        this.setGoodsId(shareRulePo.getGoodsId());
        this.setGmtModified(shareRulePo.getGmtModified());
        this.setGmtCreate(shareRulePo.getGmtCreate());
        this.setBeDeleted(shareRulePo.getBeDeleted());
        this.setId(shareRulePo.getId());
        this.setShareLevelStrategy(shareRulePo.getShareLevelStrategy());

    }
    public void toStrategyList(){

        Strategy strategy= new Strategy();
        List<Strategy>strategies=new ArrayList<>();
        JsonObject jObject = new JsonParser().parse(this.getShareLevelStrategy()).getAsJsonObject();
        JsonArray array = jObject.get("strategy").getAsJsonArray();

        for(JsonElement jsonElement : array){

            JsonObject jo = jsonElement.getAsJsonObject();
            strategy.lowerBound = jo.get("lowerbound").getAsInt();
            strategy.upperBound = jo.get("upperbound").getAsInt();
            strategy.discountRate = new BigDecimal(jo.get("rate").getAsString());

            strategies.add(strategy);
        }

        this.setShareType(jObject.get("type").getAsInt());
        this.setStrategyList(strategies);
    }
    public void toShareLevelStrategy() {

        List<String> strategyString=new ArrayList<>();
        Gson gson=new Gson();
        JsonObject jsonObject=new JsonObject();

        for(int i =0; i<this.getStrategyList().size();i++){
            Map<String,String> numToString=new LinkedHashMap<>(this.getStrategyList().size()) ;
            numToString.put("lowerbound",this.getStrategyList().get(i).lowerBound.toString());
            numToString.put("upperbound",this.getStrategyList().get(i).upperBound.toString());
            numToString.put("rate",this.getStrategyList().get(i).discountRate.toString());

            String strategies=gson.toJson(numToString);
            logger.debug("从list到string"+strategies);

            strategyString.add(strategies);
        }

        jsonObject.add("strategy",gson.toJsonTree(strategyString));
        jsonObject.add("type",gson.toJsonTree(gson.toJson(this.getShareType())));
        this.setShareLevelStrategy(jsonObject.toString());

        logger.debug(jsonObject.toString());
    }

    public Integer calculateRebate(ShareItem shareItem,BigDecimal price) {
        if(this.getStrategyList()==null){
            this.toStrategyList();
        }
        //根据successNum所在区间得到返点比例
        Integer successNum=shareItem.getSuccessNum();
        //如果成功次数小于规则所规定策略的最低值，无返点
        if (successNum < this.getStrategyList().get(0).lowerBound * 100) {
            return null;
        }
        else {
            int n = this.getStrategyList().size();
            int level = 0;
            //找到分享记录所对应的区间
            for (int i = 0; i < n; i++) {
                if (successNum > this.getStrategyList().get(i).lowerBound * 100) {
                    level++;
                } else {
                    break;
                }
            }
            if (level == n) {
                level = n - 1;
            }
            BigDecimal discountRate=this.getStrategyList().get(level).discountRate;
            BigDecimal xRebate=new BigDecimal("100");
            Integer rebate;
            discountRate=discountRate.multiply(xRebate);
            xRebate=price.multiply(discountRate);
            // 向下取整
            rebate=xRebate.setScale(0, BigDecimal.ROUND_DOWN ).intValue();
            return rebate;
        }
    }

}
