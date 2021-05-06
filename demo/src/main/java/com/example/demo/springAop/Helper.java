package com.example.demo.springAop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class Helper {

    @Before("execution(public void show())")
    public void log(){
        System.out.println("Logged files");
    }

    @AfterReturning(pointcut="execution(* com.example.demo.springAop.Alien.*(..))",returning = "result")
    public void somethingReturning(JoinPoint joinPoint, String result){
        System.out.println(joinPoint.getSignature().getName());
        System.out.println("Returning "+result);
    }
}
