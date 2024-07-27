package com.learn.reptile.config.db;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author zhangyugu
 * @Date 2021/3/18 10:19 上午
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.learn.reptile.mapper"})
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); // mybatis-plus分页插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()); // 乐观锁mybatis插件
        return interceptor;
    }
}
