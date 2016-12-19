package com.granium.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sasha.Chepurnoi on 19.12.16.
 */
@Component
public class RequestStatisticInterceptor implements AsyncHandlerInterceptor {

    private ThreadLocal<Long> time = new ThreadLocal<>();

    private static final Logger log = LoggerFactory.getLogger(RequestStatisticInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        time.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long duration = System.currentTimeMillis() - time.get();
        time.remove();
        log.info("[Time: {} ms] {} {}", duration, request.getMethod(), request.getRequestURI());
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //concurrent handling cannot be supported here
        time.remove();
    }
}
