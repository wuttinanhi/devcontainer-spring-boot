package com.example.demo.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.annotation.UseGuard;
import com.example.demo.guard.IGuardHandler;
import com.example.demo.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    AuthService authService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        System.out.println("preHandle");
        System.out.println("METHOD > " + method.getName());
        System.out.println("REQUEST > " + httpServletRequest.getRequestURL());

        // Annotation[] annotation = method.getAnnotations();

        // System.out.println("==================");
        // for (Annotation eachAnnotation : annotation) {
        // System.out.println(eachAnnotation.getClass().getName());
        // System.out.println(eachAnnotation.getClass().getCanonicalName());
        // System.out.println(eachAnnotation.getClass().getTypeName());
        // System.out.println(eachAnnotation.annotationType().getSimpleName());
        // System.out.println(eachAnnotation instanceof SecureWithJwt);
        // System.out.println("+++");
        // }
        // System.out.println("==================");

        if (method.isAnnotationPresent(UseGuard.class) == true) {
            UseGuard useGuard = method.getAnnotation(UseGuard.class);
            Class<?>[] guards = useGuard.value();

            for (Class<?> eachClass : guards) {
                System.out.println("CLASS >>> " + eachClass.getSimpleName());
                IGuardHandler guardHandler = (IGuardHandler) eachClass.getDeclaredConstructor().newInstance();
                System.out.println("GUARD HANDLER VALUE : " + guardHandler.process());
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

        System.out.println("afterCompletion");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        System.out.println("postHandle");
    }

}
