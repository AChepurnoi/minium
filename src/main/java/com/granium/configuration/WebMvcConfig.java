package com.granium.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Sasha.Chepurnoi on 19.12.16.
 */
@Configuration
@EnableCaching
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private RequestStatisticInterceptor requestStatisticsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestStatisticsInterceptor).addPathPatterns("/**");
    }
}
