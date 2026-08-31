package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 这个类给mp增加分页处理
// 给类打标签，声明这是 Spring 的配置类；
@Configuration
public class MybatisPlusConfig {

    // 给方法打标签，将方法返回的实例注册到 Spring IoC 容器中，交由 Spring 统一管理。
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
