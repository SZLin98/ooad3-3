package com.example.demo.controller;

import com.example.demo.controllerInterface.LogController;
import com.example.demo.domain.Log;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author candy
 */
@RestController
public class LogControllerMP implements LogController {
    @Override
    public Log writeLog(Log log) {
        return null;
    }
}
