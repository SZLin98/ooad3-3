package com.example.demo.controller;





import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Payment;
import com.example.demo.service.PaymentService;

import com.example.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author chei1
 */
@RestController("")
public class PaymentController {
    private static final Logger LOG= LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    PaymentService paymentService;


    /**
     * 获取paySn并创建新的payment
     * @param payment
     * @return 创建的payment
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public Object addPayment(@RequestBody Payment payment){
        return paymentService.addPayment(payment);
//        }
    }

    /**
     * 更新payment为支付成功
     * @param paySn
     * @return 支付成功的payment
     */
    @RequestMapping(value = "/payment/{id}",method =RequestMethod.PUT)
    public Object putPayment(@PathVariable("id") String paySn){
        return paymentService.putPayment(paySn);
    }

    @RequestMapping(value = "admin/payment/{id}",method =RequestMethod.PUT)
    public boolean deletePayment(@PathVariable("id") String paySn){
        boolean beSuccessful=paymentService.deletePayment(paySn);
        return beSuccessful;
    }

    @RequestMapping(value = "admin/payment",method = RequestMethod.GET)
    public List<Payment> selectAllPayment(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer limit){
        return paymentService.selectAllPayment(page,limit);
    }

    @RequestMapping(value = "admin/payment/{id}",method = RequestMethod.GET)
    public Payment selectPayment(@PathVariable("id") String paySn){
        return paymentService.selectPayment(paySn);
    }

    /**
     * 通过orderId获取payment
     * @param orderId
     * @return List<Payment>
     */
    @RequestMapping(value = "/payment/{orderId}", method = RequestMethod.GET)
    public Object getPaymentByOrderId(@PathVariable("orderId") Integer orderId){

        List<Payment> paymentList= paymentService.selectByOrderId(orderId);
        Object retObj = ResponseUtil.ok(paymentList);
        LOG.info("submit:"+retObj);

        return retObj;

    }


}

