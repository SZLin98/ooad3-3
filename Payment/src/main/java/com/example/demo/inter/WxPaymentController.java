package com.example.demo.inter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chei1
 */

@RestController
public interface WxPaymentController {
    /**
     * 调用WxPayment模块
     * @return paySn
     */
    @PostMapping("wxpayment")
    String unifiedWxPayment();


//    Object requestWxPayment(String paySn);

}



