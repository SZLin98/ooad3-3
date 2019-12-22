package com.example.demo.inter;

import com.example.demo.domain.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author chei1
 */

@FeignClient(name = "wxPaymentService",url = "http://106.15.249.35:7778")
public interface WxPaymentController {
    /**
     * 获取支付码
     * @param payment
     * @return paySn
     */
    @PostMapping("wxpayment")
    String unifiedWxPayment(Payment payment);

    /**
     * 模拟WX模块支付
     * @param paySn
     * @return
     */
    @RequestMapping(value = "/wxpayment/{id}/status",method = RequestMethod.PUT)
    boolean requestWxPayment(@PathVariable("id") String paySn);


}



