package com.hw.spring.auth.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLoging {
    @AfterReturning(pointcut = "execution(public * com.hw.spring.auth.controllers.AuthController.createAuthToken(..))",
            returning = "result")
    public void showUserLoginAfterAuth(JoinPoint joinPoint, ResponseEntity<?> result) {
        String request = null;
        Object[] obj = joinPoint.getArgs();
        if (result.getStatusCode().value() == 200) {
            for (Object o : obj) {
                request = o.toString();
            }
            String[] parseRequest = request.split("=");
            parseRequest = parseRequest[1].split(",");
            System.out.println("Пользователь: "+parseRequest[0]+" прошел авторизацию");
        }
    }
}
