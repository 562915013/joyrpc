快速开始
===

## 1. 依赖

需要安装 JDK 8 及以上 和 Maven 3 以上
   
```xml
<dependency>
  <groupId>io.joyrpc</groupId>
  <artifactId>joyrpc-all</artifactId>
  <version>最新版本</version>
</dependency>
```
## 2. 演示例子

### 2.1. API方式

#### 2.1.1. 编写服务端实现

  - 创建接口
  
      ```java
      /**
       * Demo interface
       */
      public interface DemoService {
        String sayHello(String str);
      }
      ```
      
  - 创建接口实现
  
      ```java
      public class DemoServiceImpl implements DemoService {
          public String sayHello(String name) {
            return "Hi " + name + ", response from provider. ";
          }
      }
      ```
  - 编写服务端代码
  
      ```java
      public class ServerMainAPI {
          public static void main(String[] args) throws Exception {
              //接口实现类
              DemoService demoService = new DemoServiceImpl();
               //服务端设置
              ServerConfig serverConfig = new ServerConfig();
              //服务提供者设置
              ProviderConfig<DemoService> providerConfig = new ProviderConfig<>();
              providerConfig.setServerConfig(serverConfig);
              providerConfig.setInterfaceClazz("io.joyrpc.service.DemoService");
              providerConfig.setRef(demoService);
              providerConfig.setAlias("joyrpc-demo");
              //发布服务
              providerConfig.export().whenComplete((v, t) -> {
                  providerConfig.open();
              });
              //hold住本地服务
              synchronized (ServerMainAPI.class) {
                  while (true) {
                    try {
                        ServerMainAPI.class.wait();
                    } catch (InterruptedException e) {
                    }
                  }
              }
          }    
      }
      ```
#### 2.1.2. 编写服务端实现

  - 拿到服务端接口
  
      通常以jar的形式将接口类提供给客户端。在此，先定义全路径相同的接口做演示。
      
       ```java
        /**
         * Demo interface
         */
        public interface DemoService {
           String sayHello(String str);
        }
    
       ```
  - 编写客户端代码
  
      ```java
      public class ClientMainAPI {
           public static void main(String[] args) throws Exception {
               //consumer设置
                ConsumerConfig<DemoService> consumerConfig = new ConsumerConfig<>();
                //直连,默认22000端口
                consumerConfig.setUrl("joyrpc://127.0.0.1:22000");
                consumerConfig.setInterfaceClazz("io.joyrpc.service.DemoService");
                consumerConfig.setAlias("joyrpc-demo");
                //异步启动
                CompletableFuture<Void> future = new CompletableFuture<Void>();
                DemoService service = consumerConfig.refer(future);
                future.get();
                //发起服务调用
                try {
                    String echo = service.sayHello("hello");
                } catch (Exception e) {
                }
                System.in.read();
              }
        }
      ```

### 2.2 Spring方式

### 2.3 SpringBoot方式 