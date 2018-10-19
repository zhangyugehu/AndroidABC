package com.zhangyugehu.androidabc.kotlin

import android.util.Log
import okhttp3.*

class AppHttpClient private constructor(){

    private var okHttpClient:OkHttpClient? = null
    init {
        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                .build()
    }
    private object Holder{val INSTANCE = AppHttpClient()}

    companion object {
        const val TAG = "AppHttpClient"
        val instance = Holder.INSTANCE
    }

    fun get(url: String, callback: Callback?){
        val request:Request = Request.Builder()
                .url(url)
                .build()
        okHttpClient!!.newCall(request).enqueue(callback)
    }

    class LoggingInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            Log.d(TAG, "${request.url()} ${chain.connection()} ${request.headers()}")
            val response = chain.proceed(request)
            Log.d(TAG, "${response.request().url()} ${response.headers()}")
            return response
        }

    }

}