package com.example.demo.controller;

import com.example.demo.controllerInterface.OrderController;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author candy
 */
@RestController
public class OrderControllerMp implements OrderController {


    @Override
    public Object userDetail(Integer userId, @NotNull Integer orderId) {
        return null;
    }
}
