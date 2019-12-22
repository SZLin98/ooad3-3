package com.example.demo.service;

import com.example.demo.domain.Payment;
import com.example.demo.inter.WxPaymentController;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;


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


    public Object addPayment(Payment payments){
        String paySn=wxPaymentController.unifiedWxPayment(payments);
        LOG.info("paySn"+paySn);
        payments.setStatusCode(0);
        payments.setPaySn(paySn);
        payments.setPayChannel(1);
        return rocketMqProvider.defaultMqProducer(payments);

    }
    public Object putPayment(String paySn){
        List<Payment> paymentList= paymentMapper.selectPay(paySn);

        if(paymentList.size()==0){

            return ResponseUtil.paymentNotFound();
        }

        else {
            Payment payment=paymentList.get(0);
            boolean paySuccess=wxPaymentController.requestWxPayment(paySn);
            if(payment.timeValid()&&paySuccess)
            {
            LocalDateTime payTime = LocalDateTime.now();
            payment.setPayTime(payTime);
            payment.setPaySn(paySn);
            payment.setStatusCode(1);
            rocketMqProvider.updateMqProducer(payment);
            Object retObj = ResponseUtil.ok(payment);
            LOG.info("submit:"+retObj);
            return retObj;
            }
            else {
                return ResponseUtil.putPayment();
            }
        }
    }

    public boolean deletePayment(String paySn){
        int deleteColumn=paymentMapper.deletePayment(paySn);
        if(deleteColumn==0){
            return false;
        }
        else {
            LOG.info("admin delete payment:"+paySn);
            return true;
        }

    }

    public List<Payment> selectAllPayment(Integer pages,Integer limit){
        Integer start=(pages-1)*limit;
        return paymentMapper.selectAllPayment(start,limit);
    }

    public Payment selectPayment(String paySn){
        List<Payment> paymentList= paymentMapper.selectPayment(paySn);
        if(paymentList.size()==0){

            return new Payment();
        }
        else {
            return  paymentList.get(0);
        }
    }

    public List<Payment> selectByOrderId(Integer orderId){
        return paymentMapper.selectByOrderId(orderId);
    }

    public Payment refundPayment(Payment payment){
        String paySn=wxPaymentController.unifiedWxPayment(payment);
        payment.setPaySn(paySn);
        payment.setStatusCode(1);
        if(paymentMapper.refundPayment(payment)!=0){
            LOG.info("Refund:"+payment.toString());
            return payment;
        }
        else {
            return new Payment();
        }
    }
}
