package com.example.controller;

import com.example.service.SchedualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Autor jiangcaijun
 * @Date 2017/11/19
 * @Time 21:02
 */
@RestController
public class HiController {

    @Autowired
    SchedualService schedualService;

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
        return "我是servie-feign：" + schedualService.sayHiFromClientOne(name);
    }
}