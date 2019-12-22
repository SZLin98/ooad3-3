package com.example.demo.service;

import com.example.demo.dao.AsyncDao;
import com.example.demo.domain.Payment;
import com.example.demo.inter.PaymentController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


/**
 * @author chei1
 */
@Service
public class WxPaymentService {
    private static final Logger LOG= LoggerFactory.getLogger(WxPaymentService.class);
    @Autowired
    PaymentController paymentController;
    @Autowired
    AsyncDao asyncDao;

    public String unifiedWxPayment(){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        int length=18;
        for(int i=0;i<length;i++){
            int number=random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



    public Object putPayment(String paySn){
        return paymentController.putPayment(paySn);
    }



}
