package com.zhangyugehu.androidabc.kotlin.frame.retrofit.designpattern.proxy0

fun main(args: Array<String>) {
    val proxyObject = ProxyObject(RealObject())
    proxyObject.operation()
}