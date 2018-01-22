
最新项目结构

```
├── spring_cloud_example
│   ├── zeus_eureka_server      //注册中心 eureka 的 client 端         
│   ├── zeus_eureka_client      //注册中心 eureka 的 client 端
│   ├── zeus_service_ribbon     //服务消费 ribbon            
│   ├── zeus_service_feign      // 服务消费 feign
│   ├── zeus_hystric            // 断路器 hystric 的 熔断机制
│   ├── zeus_zuul               // 路由网关 zuul 的 请求分发
│   ├── zeus_config_server      //配置中心 config 的 server 端          
│   ├── zeus_config_client      //配置中心 config 的 client 端
│   └── README.md
```

#### 1. 参考链接
* [史上最简单的 SpringCloud 教程 | 终章 - 方志朋的专栏 - CSDN博客](http://blog.csdn.net/forezp/article/details/70148833)

#### 2. 疑问与解决
##### 1. 疑问

* 微服务注册到Eureka Server上的粗粒度过程
* 为什么appname是大写。
* appName的配置：spring.application.name与eureka.instance.appname，及它们的优先级。

参考链接:
* [理解微服务注册到Eureka Server上的过程（以appname为例） | Spring Cloud|周立](http://www.itmuch.com/spring-cloud-code-read/spring-cloud-code-read-eureka-registry-appname/)

##### 2. 疑问
* controller层加了@RestController 注解，项目启动时，控制台打印，没有打印出相应的mapping ，为什么？

解答:
* SpringBoot项目的Bean装配默认规则是根据Application类所在的包位置从上往下扫描！ “Application类”是指SpringBoot项目入口类。这个类的位置很关键：如果Application类所在的包为：io.github.gefangshuai.app，则只会扫描io.github.gefangshuai.app包及其所有子包，如果service或dao所在包不在io.github.gefangshuai.app及其子包下，则不会被扫描！
* 如何进行改变这种扫描包的方式呢，原理很简单就是：@ComponentScan注解进行指定要扫描的包以及要扫描的类。

参考链接:
* [关于SpringBoot bean无法注入的问题（与文件包位置有关）改变自动扫描的包 - CSDN博客](http://blog.csdn.net/u014695188/article/details/52263903)


#### 3.知识点
##### 1.eureka
* 1. Eureka 架构机制 
我们的 Eureka 集群服务其实就是靠 Server 与 Client 之间的交互来实现的。 
* 2. Eureka Server 具有服务定位/发现的能力，在各个微服务启动时，会通过Eureka Client向Eureka Server进行注册自己的信息（例如网络信息）。 
* 3. 一般情况下，微服务启动后，Eureka Client 会周期性向 Eureka Server 发送心跳检测(默认周期为30秒)以注册/更新自己的信息。 
* 4. 如果 Eureka Server 在一定时间内(默认90秒)没有收到 Eureka Client 的心跳检测，就会注销掉该微服务点。 
* 5. 同时，Eureka Server 它本身也是 Eureka Client，多个 Eureka Server 通过复制注册表的方法来完成服务注册表的同步从而达到集群的效果

参考链接:
* [spring cloud 学习(二)关于 Eureka 的学习笔记 - CSDN博客](http://blog.csdn.net/u011244202/article/details/54985077?utm_source=itdadao&utm_medium=referral)

#### 4.学习进度
##### 1. eureka 服务的注册与发现（2017年11月18号）
eureka server(port:8761) 和 eureka client(port:8762) 启动后，实现服务的注册与发现，如下图：



##### 2. ribbon 服务消费者（rest+ribbon）（2017年11月19号）
接上节，利用rest+ribbon，实现负载均衡，访问了不同的端口的服务实例。



此时的架构，如下：

* 一个服务注册中心，eureka server,端口为8761
* service-hi工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册
* sercvice-ribbon端口为8764,向服务注册中心注册
* 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口；