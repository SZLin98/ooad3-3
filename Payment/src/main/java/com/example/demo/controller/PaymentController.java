package com.example.demo.controller;





import com.example.demo.domain.Payment;
import com.example.demo.service.PaymentService;

import com.example.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author chei1
 */
@RestController("")
public class PaymentController {
    private static final Logger LOG= LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    PaymentService paymentService;


    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public Object addPayment(@RequestBody Payment payment){

        Payment retBody=paymentService.addPayment(payment);
        Object retObj = ResponseUtil.ok(retBody);
        LOG.info("submit:"+retObj);
        return retBody;
    }

    @RequestMapping(value = "/payments/{id}",method =RequestMethod.PUT)
    public Payment putPayment(@PathVariable("id") String paySn){
        Payment retBody=paymentService.putPayment(paySn);
        Object retObj = ResponseUtil.ok(retBody);
        LOG.info("submit:"+retObj);
        return retBody;
    }


}

