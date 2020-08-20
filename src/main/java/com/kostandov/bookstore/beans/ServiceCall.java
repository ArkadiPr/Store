package com.kostandov.bookstore.beans;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceCall {
    private Map<String,Integer> serviceMethodCalls;

    {
        serviceMethodCalls=new HashMap<>();
    }


    public void addMethodCall(String methodName){
        if(serviceMethodCalls.containsKey(methodName)){
            serviceMethodCalls.put(methodName,serviceMethodCalls.get(methodName)+1);
        }else {
            serviceMethodCalls.put(methodName,1);
        }
    }

    public Map<String,Integer> getAllHistory(){
        return serviceMethodCalls;
    }
}
