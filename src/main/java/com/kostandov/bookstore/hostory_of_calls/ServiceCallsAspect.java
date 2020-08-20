package com.kostandov.bookstore.hostory_of_calls;

import com.kostandov.bookstore.beans.ServiceCall;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceCallsAspect {

    private ServiceCall serviceCall;

    public ServiceCallsAspect(ServiceCall serviceCall) {
        this.serviceCall = serviceCall;
    }

    @Before("execution(public * com.kostandov.bookstore.services.*.*(..))")
    public void beforeServiceMethodCalls(JoinPoint joinPoint){
        serviceCall.addMethodCall(joinPoint.getSignature().toString());
    }

}
