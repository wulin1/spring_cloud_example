package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心config 的 server 端
 * @Author jiangcaijun
 */
@SpringBootApplication
@EnableConfigServer
public class ZeusConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeusConfigServerApplication.class, args);
	}
}
