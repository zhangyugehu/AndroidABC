package com.zhangyugehu.androidabc.kotlin.threadpool

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadPool private constructor(){

    private val mExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            sPoolWorkQueue
    )

    private object Inner{
        val instance:ThreadPool = ThreadPool()
    }
    companion object {
        fun get():ThreadPool{
            return Inner.instance
        }
        private const val CORE_POOL_SIZE: Int = 3
        private const val MAXIMUM_POOL_SIZE: Int = 128
        private const val KEEP_ALIVE_SECONDS: Long = 128
        private val sPoolWorkQueue = LinkedBlockingQueue<Runnable>()
    }

    fun execute(command: Runnable){
        mExecutor.execute(command)
    }
}