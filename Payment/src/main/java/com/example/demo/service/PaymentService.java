package com.example.demo.service;

import com.example.demo.domain.OrderPo;
import com.example.demo.domain.Payment;
import com.example.demo.inter.WxPaymentController;
import com.example.demo.mapper.PaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDateTime;


/**
 * @author chei1
 */
@Service

public class PaymentService {
    private static final Logger LOG= LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    RocketMqProvider rocketMqProvider;
    @Autowired
    WxPaymentController wxPaymentController;
    @Autowired
    PaymentMapper paymentMapper;

    public Payment addPayment(Payment payments){

        Integer payChannel=1;


        //获取当前的日期
        LocalDateTime beginTime = LocalDateTime.now();
        LocalDateTime endTime = beginTime.plusHours(2L);
        LOG.info("beginTime:"+beginTime);
        LOG.info("endTime:"+endTime);
        String paySn=wxPaymentController.unifiedWxPayment();
        LOG.info("paySn"+paySn);



        payments.setBeginTime(beginTime);
        payments.setEndTime(endTime);

        payments.setPayChannel(payChannel);
        payments.setPaySn(paySn);




        rocketMqProvider.defaultMqProducer(payments);
        return payments;

//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObjectPaymentInfo = new JSONObject();
//        jsonObjectPaymentInfo.put("orderId",orderId);
//        jsonObjectPaymentInfo.put("payChannel",payChannel);
//        jsonObjectPaymentInfo.put("beginTime",beginTime);
//        jsonObjectPaymentInfo.put("endTime",endTime);
//        System.out.println(jsonObjectPaymentInfo);


    }
    public Payment putPayment(String paySn){
        Payment payment = paymentMapper.selectPay(paySn).get(0);
//        Payment payment=new Payment();
        LocalDateTime payTime = LocalDateTime.now();
        payment.setPayTime(payTime);
        payment.setPaySn(paySn);
        payment.setBeSuccessful(true);
        rocketMqProvider.updateMqProducer(payment);
        return payment;
    }

}
