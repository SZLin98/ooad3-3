package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Payment;
import com.example.demo.mapper.PaymentMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.demo.mapper.PaymentMapper;


/**
 * @author chei1
 */
@Service("RocketMQConsumer")
public class RocketMqConsumer {
    private static final Logger LOG= LoggerFactory.getLogger(RocketMqConsumer.class);
    /**
     * 消费者的组名
     */
    @Value("${apache.rocketmq.consumer.PushConsumer}")
    private String consumerGroup;

    @Value("${apache.rocketmq.consumer.PushConsumer1}")
    private String consumerGroup1;
    @Autowired
    PaymentMapper paymentMapper;
    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

//    @Resource
//    PaymentMapper paymentMapper;

//    @Resource
//    WxPaymentController wxPaymentController;

    @PostConstruct
    public void defaultMqPushConsumer() {


        //消费者的组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);

        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);

        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("ADD", "push");

            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                //->为Java8的lambda表达式,就是匿名函数,具体可以参考该文章https:
                // segmentfault.com/q/1010000007518474。
                try {
                    for (MessageExt messageExt : list) {

                        LOG.info("messageExt: " + messageExt);

                        String messageBody = new String(messageExt.getBody());


                        Payment payments = new Payment();


                        LOG.info("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + messageBody);//输出消息内容

                        JSONObject jsonObject = JSONObject.parseObject(messageBody);
                        BigDecimal actualPrice = new BigDecimal(jsonObject.getString("actualPrice"));
                        payments.setActualPrice(actualPrice);
                        Integer orderId = new Integer(jsonObject.getString("orderId"));
                        payments.setOrderId(orderId);
                        Integer payChannel = new Integer(jsonObject.getString("payChannel"));
                        payments.setPayChannel(payChannel);
                        payments.setBeginTime(LocalDateTime.parse(jsonObject.getString("beginTime")));
                        payments.setEndTime(LocalDateTime.parse(jsonObject.getString("endTime")));
                        payments.setPaySn(jsonObject.getString("paySn"));
                        if (payments.getPaySn() != null){
                            paymentMapper.create(payments);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void updateMqPushConsumer() {


        //消费者的组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup1);

        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);

        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("PUT", "update");

            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                //->为Java8的lambda表达式,就是匿名函数,具体可以参考该文章https:
                // segmentfault.com/q/1010000007518474。
                try {
                    for (MessageExt messageExt : list) {

                        LOG.info("messageExt: " + messageExt);//输出消息内容

                        String messageBody = new String(messageExt.getBody());


                        Payment payments = new Payment();


                        LOG.info("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + messageBody);//输出消息内容
                        JSONObject jsonObject = JSONObject.parseObject(messageBody);
                        payments.setPaySn(jsonObject.getString("paySn"));
                        payments.setBeSuccessful(true);
                        payments.setPayTime(LocalDateTime.parse(jsonObject.getString("payTime")));
                        LOG.info(payments.toString());


                        if (payments.getPaySn() != null){
                            paymentMapper.put(payments);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
