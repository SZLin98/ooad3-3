package com.example.demo.mapper;

import com.example.demo.domain.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 通过paySn删除payment
     * @param paySn
     * @return 删除行数
     */
   int deletePayment(String paySn);

    /**
     * 分页查看支付信息
     * @param start
     * @param limit
     * @return List
     */
   List<Payment> selectAllPayment( Integer start, Integer limit);

    /**
     * 查看某条支付
     * @param paySn
     * @return Paymen
     */
   List<Payment> selectPayment(String paySn);

    /**
     * 根据订单号查看相应支付
     * @param orderId
     * @return List
     */
   List<Payment> selectByOrderId(Integer orderId);

    /**
     * 更新退款信息
     * @param payment
     * @return 创建的行数
     */
   int refundPayment(Payment payment);
}
