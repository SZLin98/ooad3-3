package com.example.demo.inter;

import com.example.demo.domain.OrderPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author chei1
 */
public interface OrderController {
    /**
     * 修改订单状态为支付成功
     * @param orderId
     * @return OrderPo
     */
    @PutMapping("/order/{id}")
    OrderPo paySuccess(@PathVariable("id") Integer orderId);
}
