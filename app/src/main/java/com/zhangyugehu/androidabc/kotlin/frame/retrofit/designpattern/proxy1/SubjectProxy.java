package com.zhangyugehu.androidabc.kotlin.frame.retrofit.designpattern.proxy1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(proxy, args);
        return null;
    }
}
