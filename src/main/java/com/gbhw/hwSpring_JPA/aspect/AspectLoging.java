package com.gbhw.hwSpring_JPA.aspect;

import com.gbhw.hwSpring_JPA.dto.jwt.JwtResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Aspect
@Component
public class AspectLoging {
    private HashMap<String, Long> servicesTimes = new HashMap<>();

    public HashMap<String, Long> getServicesTimes() {
        return servicesTimes;
    }

    @Around("execution(public * com.gbhw.hwSpring_JPA.services.*Service.*(..))")
    public Object timeMethodsOfService(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Long begin = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        Long end = System.currentTimeMillis();
        Long resultTime = end - begin;
        if (!servicesTimes.containsKey(joinPoint.getSignature().getDeclaringType().getSimpleName())) {
            servicesTimes.put(className, resultTime);
        } else {
            Long currentTime = servicesTimes.get(className);
            servicesTimes.put(className, currentTime + resultTime);
        }
        return obj;
    }

    @AfterReturning(pointcut = "execution(public * com.gbhw.hwSpring_JPA.controllers.AuthController.createAuthToken(..))",
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
