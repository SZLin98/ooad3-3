package com.example.demo.inter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author chei1
 */
@RestController
public class FalseWxPayment implements WxPaymentController{
    @Override
    public String unifiedWxPayment() {
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
//    @Override
//    public Object requestWxPayment(String paySn){
//        return null;
//    }
}
