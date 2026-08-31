package org.john.worldspring.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyWorldSpringApplication {

    /**
     * 题目1【SpringBoot项目搭建+CRUD】
     * 需求：实现用户管理简单接口
     * 实体User：id、username、age、phone、createTime
     * 1. 使用SpringBoot + MyBatis‑Plus搭建，使用MP内置BaseMapper、IService完成CRUD；
     * 2. 写接口：新增用户、根据id查询用户、分页查询用户列表（MP分页插件，必须配置分页拦截器）；
     * 3. 增加全局异常处理器 @RestControllerAdvice，统一捕获异常返回标准JSON；
     * 4. 增加参数校验：新增用户时username不能为空，age范围1‑120，参数非法返回错误提示。
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MyWorldSpringApplication.class, args);
    }
}
