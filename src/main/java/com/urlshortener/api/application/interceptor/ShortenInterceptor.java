package com.urlshortener.api.application.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ShortenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String hostUrl = request.getScheme() + "://" + request.getHeader("host") + "/";
        request.setAttribute("hostUrl", hostUrl);
        return true;
    }
}
