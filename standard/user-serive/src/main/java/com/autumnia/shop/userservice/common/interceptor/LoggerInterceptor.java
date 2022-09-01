package com.autumnia.shop.userservice.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("");
        log.debug("##############################  Start    ##############################");
        log.debug(" Request URI \t:  " + request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("##############################  End  ##################################");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

// jsp 파일이 있는 경우
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
//        log.debug("##############################  Complete ##############################");
//        HandlerInterceptor.super.afterCompletion(request, response, handler, e);
//    }
}
