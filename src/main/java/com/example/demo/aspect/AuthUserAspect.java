package com.example.demo.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.annotation.AuthUser;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthUserAspect {
    @Autowired
    AuthService authService;

    @Around("execution(* *(.., @com.example.demo.annotation.AuthUser (*), ..))")
    public Object aop(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String accessToken = authService.splitAuthorizationBearer(authorizationHeader);

        User authUser = null;

        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] methodAnnotations = method.getParameterAnnotations();

        for (int parameterIndex = 0; parameterIndex < methodAnnotations.length; parameterIndex++) {
            Annotation[] annotationArray = methodAnnotations[parameterIndex];

            for (Annotation annotation : annotationArray) {
                if (annotation instanceof AuthUser == false) {
                    continue;
                }

                if (authUser == null) {
                    authUser = authService.getUserFromAccessToken(accessToken);
                }

                args[parameterIndex] = authUser;
            }
        }

        return joinPoint.proceed(args);
    }
}
