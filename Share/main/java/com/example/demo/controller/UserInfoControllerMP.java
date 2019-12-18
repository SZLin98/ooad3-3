package com.example.demo.controller;

import com.example.demo.controllerInterface.UserInfoController;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author candy
 */
@RestController

public class UserInfoControllerMP implements UserInfoController {
    @Override
    public Object isValid(Integer id) {
        return true;
    }
}
