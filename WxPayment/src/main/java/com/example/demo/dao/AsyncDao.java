package com.example.demo.dao;

import com.example.demo.domain.Payment;
import com.example.demo.service.WxPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chei1
 */
@Service
public class AsyncDao {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncDao.class);
    @Autowired
    WxPaymentService wxPaymentService;

    @org.springframework.scheduling.annotation.Async
    public Object paySuccess(String paySn){
        try {
            Thread.sleep(3000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        LOG.info("submit pay success");
        return wxPaymentService.putPayment(paySn);
    }
}
