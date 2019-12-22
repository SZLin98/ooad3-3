package com.example.demo.controller;



import com.example.demo.domain.Payment;

import com.example.demo.service.WxPaymentService;
import com.example.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author chei1
 */
@RestController()
public class WxPaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(WxPaymentController.class);
    @Autowired
    WxPaymentService wxPaymentService;

    @RequestMapping(value="wxpayment",method = RequestMethod.POST)
    public String unifiedWxPayment(@RequestBody Payment payment) {
        String paySn=wxPaymentService.unifiedWxPayment();
        Object retObj = ResponseUtil.ok(paySn);
        LOG.info("submit:" + retObj);
        return paySn;
    }

    @RequestMapping(value = "/wxpayment/{id}/status",method =RequestMethod.PUT)
    public boolean requestWxPayment(@PathVariable("id") String paySn){
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            LOG.info(paySn+":Success!");
            return true;


    }

    @RequestMapping(value="wxpayment/{id}/refund",method = RequestMethod.PUT)
    public Object refund(@PathVariable("id") String refundWhom, String
           paySn, BigDecimal actualPrice){
        Object retObj = wxPaymentService.putPayment(paySn);
        LOG.info("submit:" + retObj);
        return retObj;
    }
}
