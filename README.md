对应项目地址为：https://github.com/jiangcaijun/spring_cloud_example

该项目的 README 已同步到简书：https://www.jianshu.com/p/def752208f6c

最新项目结构

```
├── spring_cloud_example
│   ├── zeus_eureka_server          //注册中心 eureka 的 client 端         
│   ├── zeus_eureka_client          //注册中心 eureka 的 client 端
│   ├── zeus_service_ribbon         //服务消费 ribbon            
│   ├── zeus_service_feign          //服务消费 feign
│   ├── zeus_hystric                //断路器 hystric 的 熔断机制
│   ├── zeus_zuul                   //路由网关 zuul 的 请求分发
│   ├── zeus_config_server          //配置中心 config 的 server 端
│   ├── zeus_config_client          //配置中心 config 的 client 端
│   ├── zeus_config_eureka_server   //配置中心 config 的 server 端的微服务化（利用eureka）
│   └── README.md
```

### 1. 参考链接
* [史上最简单的 SpringCloud 教程 | 终章 - 方志朋的专栏 - CSDN博客](http://blog.csdn.net/forezp/article/details/70148833)

### 2. 疑问与解决
#### 1. 疑问1
* 微服务注册到Eureka Server上的粗粒度过程
* 为什么appname是大写。
* appName的配置：spring.application.name与eureka.instance.appname，及它们的优先级。

参考链接:
* [理解微服务注册到Eureka Server上的过程（以appname为例） | Spring Cloud|周立](http://www.itmuch.com/spring-cloud-code-read/spring-cloud-code-read-eureka-registry-appname/)

#### 2. 疑问2
* controller层加了@RestController 注解，项目启动时，控制台打印，没有打印出相应的mapping ，为什么？

解答:
* SpringBoot项目的Bean装配默认规则是根据Application类所在的包位置从上往下扫描！ “Application类”是指SpringBoot项目入口类。这个类的位置很关键：如果Application类所在的包为：io.github.gefangshuai.app，则只会扫描io.github.gefangshuai.app包及其所有子包，如果service或dao所在包不在io.github.gefangshuai.app及其子包下，则不会被扫描！
* 如何进行改变这种扫描包的方式呢，原理很简单就是：@ComponentScan注解进行指定要扫描的包以及要扫描的类。

参考链接:
* [关于SpringBoot bean无法注入的问题（与文件包位置有关）改变自动扫描的包 - CSDN博客](http://blog.csdn.net/u014695188/article/details/52263903)


### 3.知识点
#### 1.eureka
*  Eureka 架构机制：我们的 Eureka 集群服务其实就是靠 Server 与 Client 之间的交互来实现的。 
*  Eureka Server 具有服务定位/发现的能力，在各个微服务启动时，会通过Eureka Client向Eureka Server进行注册自己的信息（例如网络信息）。 
* 一般情况下，微服务启动后，Eureka Client 会周期性向 Eureka Server 发送心跳检测(默认周期为30秒)以注册/更新自己的信息。 
* 如果 Eureka Server 在一定时间内(默认90秒)没有收到 Eureka Client 的心跳检测，就会注销掉该微服务点。 
* 同时，Eureka Server 它本身也是 Eureka Client，多个 Eureka Server 通过复制注册表的方法来完成服务注册表的同步从而达到集群的效果

参考链接:
* [spring cloud 学习(二)关于 Eureka 的学习笔记 - CSDN博客](http://blog.csdn.net/u011244202/article/details/54985077?utm_source=itdadao&utm_medium=referral)

### 4.框架搭建进度
#### 1. eureka 服务的注册与发现（2017年11月18号）
eureka server(port:8761) 和 eureka client(port:8762) 启动后，实现服务的注册与发现，如下图：
![1.1.png](http://upload-images.jianshu.io/upload_images/3110065-b582c91f17fa94f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![1.2.png](http://upload-images.jianshu.io/upload_images/3110065-f819af34ee3a270f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2. 服务消费者（rest+ribbon）（2017年11月19号）
接上节，利用rest+ribbon，实现负载均衡，访问了不同的端口的服务实例。
![2.1.png](http://upload-images.jianshu.io/upload_images/3110065-f8a9493139375fd2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![2.2.png](http://upload-images.jianshu.io/upload_images/3110065-93716fb032330fd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

此时的架构，如下：

![2.3.png](http://upload-images.jianshu.io/upload_images/3110065-9cb737b1aac6d335.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 一个服务注册中心，eureka server,端口为8761
* service-hi工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册
* sercvice-ribbon端口为8764,向服务注册中心注册
* 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口；

#### 3. 服务消费者（Feign）（2017年11月19号）
接上节，利用feign，实现负载均衡，访问了不同的端口的服务实例。

![3.1.png](http://upload-images.jianshu.io/upload_images/3110065-4906ccd240c794bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![3.2.png](http://upload-images.jianshu.io/upload_images/3110065-2c141f08c5405a13.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 4. 断路器（Hystric）(2017年11月28号)
##### 4.1 在ribbon使用断路器
hystric（8764）的service层代码如下：
```
/**
 * 通过之前注入ioc容器的restTemplate来消费service-hi服务的“/hi”接口，
 * 在这里我们直接用的程序名替代了具体的url地址，在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名
 * @param name
 * @return
 */
@Override
@HystrixCommand(fallbackMethod = "hiError") //该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法
public String hiService(String name) {
    return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
}

@Override
public String hiError(String name) {
    return "hi,"+name+",sorry,error!";
}
```

依次启动eureka_server（8761）、eureka_client（8762）、hystric（8764），访问：http://localhost:8764/hi?name=dd，如下图：

![4.1.png](http://upload-images.jianshu.io/upload_images/3110065-8197e6bac00227e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

将eureka_client（8762）项目停掉，再次访问该页面：

![4.2.png](http://upload-images.jianshu.io/upload_images/3110065-bd0970740569b419.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

证明：eureka_client（8762） 工程不可用的时候，hystric（8764）调用eureka_client（8762）的API接口时，会执行快速失败，直接返回一组字符串，而不是等待响应超时，这很好的控制了容器的线程阻塞。

#### 5. zuul （2017年12月27号）
zuul 主要功能 路由转发和过滤器

![5.1.png](http://upload-images.jianshu.io/upload_images/3110065-0327da1dd1f62a1d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

现象（路由转发）：

![5.2.png](http://upload-images.jianshu.io/upload_images/3110065-53e48c2617e19524.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![5.3.png](http://upload-images.jianshu.io/upload_images/3110065-2e6d20536195a708.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 6. 配置中心（config from git）(2018年01月20号)

![6.1.png](http://upload-images.jianshu.io/upload_images/3110065-dc7170507f63ec62.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

ZeusConfigServerApplication： config 的 server 端
ZeusConfigClientApplication： config 的 client 端
注意：关于config client 端，使用bootstrap.properties 是因为：bootstrap.properties的加载是先于application.properties

注意：
> Spring Boot中application.yml与bootstrap.yml的区别（转）
说明：其实yml和properties文件是一样的原理，主要是说明application和bootstrap的加载顺序。且一个项目上要么yml或者properties，二选一的存在。
Bootstrap.yml（bootstrap.properties）在application.yml（application.properties）之前加载，就像application.yml一样，但是用于应用程序上下文的引导阶段。它通常用于“使用Spring Cloud Config Server时，应在bootstrap.yml中指定spring.application.name和spring.cloud.config.server.git.uri”以及一些加密/解密信息。技术上，bootstrap.yml由父Spring ApplicationContext加载。父ApplicationContext被加载到使用application.yml的之前。
例如，当使用Spring Cloud时，通常从服务器加载“real”配置数据。为了获取URL（和其他连接配置，如密码等），您需要一个较早的或“bootstrap”配置。因此，您将配置服务器属性放在bootstrap.yml中，该属性用于加载实际配置数据（通常覆盖application.yml [如果存在]中的内容）。

访问配置信息的URL与配置文件的映射关系如下：
  * /{application}/{profile}[/{label}]
  * /{application}-{profile}.yml
  * /{label}/{application}-{profile}.yml
  * /{application}-{profile}.properties
  * /{label}/{application}-{profile}.properties

启动 server 端后，访问：http://localhost:8888/config-client/dev ，如下图所示：

![6.2.png](http://upload-images.jianshu.io/upload_images/3110065-1e45823e2222f950.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![6.3.png](http://upload-images.jianshu.io/upload_images/3110065-2cce37ec86b09d22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

再启动 client 端 ，访问： http://localhost:8881/hi ，如下图所示：

![6.4.png](http://upload-images.jianshu.io/upload_images/3110065-64cbe613797afa96.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

证明： config-client从config-server获取了foo的属性，而config-server是从git仓库读取的,

当前架构，如图：

![6.5.png](http://upload-images.jianshu.io/upload_images/3110065-a772e70d27e076c3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 7. 高可用配置中心（config 与 eureka）(2018年01月25号)

![7.1.png](http://upload-images.jianshu.io/upload_images/3110065-7616637a2a598220.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![7.2.png](http://upload-images.jianshu.io/upload_images/3110065-44e3ceb96c59f378.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

访问： http://localhost:8881/hi ，如下图所示：

![7.3.png](http://upload-images.jianshu.io/upload_images/3110065-d22441be7a0b0efa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

当前架构，如图：

![7.4.png](http://upload-images.jianshu.io/upload_images/3110065-219a16b211ba42ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
