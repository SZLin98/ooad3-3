package com.example.demo.mapper;

import com.example.demo.domain.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chei1
 */

@Mapper
@Repository
public interface PaymentMapper {
    /**
     * 创建一个payment
     * @param payments 增加的payment对象
     * @return 创建的行数
     */
    int create(Payment payments);

    /**
     *更新payment
     * @param payments 更新payment对象支付成功
     * @return 更新的行
     */
    int put(Payment payments);

    /**
     * 通过PaySn获取Payment
     * @param paySn
     * @return 找到的Payment
     */
    List<Payment> selectPay(String paySn);
}
