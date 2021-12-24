package com.predict.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author hp
 */
@RestController
public class CaculatorSimulatorController {
    @RequestMapping("/caculator")
    public String calculator(){
        BigDecimal testData = new BigDecimal("0.0001");
        for (int i = 0; i < 1000000; i++) {
            testData = testData.add(BigDecimal.valueOf(Math.sqrt(testData.doubleValue())));
        }
        return "Ok!";
    }
}
