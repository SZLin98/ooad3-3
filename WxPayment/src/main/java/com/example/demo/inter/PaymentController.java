package com.example.demo.inter;

import com.example.demo.domain.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chei1
 */
@FeignClient(name = "paymentService",url = "http://106.15.249.35:7777")
public interface PaymentController {
    /**
     * 修改支付状态为支付成功
     * @param paySn
     * @return
     */
    @RequestMapping(value = "/payment/{id}",method = RequestMethod.PUT)
    Object putPayment(@PathVariable("id") String paySn);
}
