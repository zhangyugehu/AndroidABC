package com.zhangyugehu.androidabc.kotlin.frame.retrofit.designpattern.proxy1;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args){
        Man man = new Man();
        SubjectProxy proxy = new SubjectProxy();
        Subject subject = (Subject) Proxy.newProxyInstance(
                man.getClass().getClassLoader(),
                man.getClass().getInterfaces(),
                proxy
        );
        subject.shopping();
    }
}
