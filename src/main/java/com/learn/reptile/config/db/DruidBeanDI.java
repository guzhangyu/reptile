package com.learn.reptile.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhangyugu
 * @Date 2021/3/18 10:17 上午
 * @Version 1.0
 */
@Configuration
public class DruidBeanDI {

    @Bean(name = "druid")
    public DruidDataSource dataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }
}
