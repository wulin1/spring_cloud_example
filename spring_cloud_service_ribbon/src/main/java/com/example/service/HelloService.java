package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Autor jiangcaijun
 * @Date 2017/11/19
 * @Time 10:49
 */
public interface HelloService {

    public String hiService(String name);

}