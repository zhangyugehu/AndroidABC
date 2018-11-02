package com.zhangyugehu.androidabc.kotlin.frame.okhttp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zhangyugehu.androidabc.R
import okhttp3.*
import java.io.File
import java.util.concurrent.TimeUnit

class OkHttpActivity : AppCompatActivity() {

    companion object {
        const val TAG = "OkHttpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .cache(Cache(File("cache"), 24 * 1024 * 1024))
            .addInterceptor {
                val request = it.request()
                Log.d(TAG, request.headers().toString())
                 it.proceed(request)
            }
            .build()

    private fun syncGet(url: String):Response{
        val request = Request.Builder().url(url).get().build()
        val call = client.newCall(request)
        // 同步请求
        return call.execute()
    }

    private fun asyncGet(url: String, callback: Callback){
        val request = Request.Builder().url(url).get().build()
        val call = client.newCall(request)
        // 异步请求
        call.enqueue(callback)
    }
}
