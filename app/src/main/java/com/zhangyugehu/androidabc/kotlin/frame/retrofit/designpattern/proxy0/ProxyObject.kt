package com.zhangyugehu.androidabc.kotlin.frame.retrofit.designpattern.proxy0

class ProxyObject(private val realObject: RealObject) : AbsObject() {
    override fun operation() {
        println("before operation...")
        realObject.operation()
        println("after operation...")
    }

    fun doSomething0(){

    }
    fun doSomething1(){

    }
}