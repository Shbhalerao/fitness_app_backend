package com.collaborate.FitnessApp.config.interceptor;

import com.collaborate.FitnessApp.domain.base.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.swing.plaf.PanelUI;

@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception{
        String userId = request.getHeader("X-User-Id");
        String role = request.getHeader("X-User-Role");

        if(userId !=null){
            UserContext.setUserId(userId);
        }
        if(role != null){
            UserContext.setRole(role);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                             Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
