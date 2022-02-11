package com.example.demo.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.annotation.UseGuard;
import com.example.demo.guard.IGuardHandler;
import com.example.demo.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GuardInterceptor implements HandlerInterceptor {
    @Autowired
    AuthService authService;

    @Autowired
    AutowireCapableBeanFactory beanFactory;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        UseGuard useGuard = null;

        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            useGuard = method.getDeclaredAnnotation(UseGuard.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (useGuard != null) {
            Class<?>[] guards = useGuard.value();

            for (Class<?> eachGuardClass : guards) {
                boolean processResult = false;

                try {
                    IGuardHandler guardInstance = (IGuardHandler) eachGuardClass.getConstructor().newInstance();

                    beanFactory.autowireBean(guardInstance);

                    processResult = guardInstance.process(request);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                if (processResult == false) {
                    throw new AccessDeniedException("Unauthorized");
                }
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
