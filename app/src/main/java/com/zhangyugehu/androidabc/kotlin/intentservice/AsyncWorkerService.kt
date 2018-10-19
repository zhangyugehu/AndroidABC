package com.zhangyugehu.androidabc.kotlin.intentservice

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.zhangyugehu.androidabc.kotlin.AppHttpClient
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.ref.WeakReference

private const val ACTION_FOO = "com.zhangyugehu.androidabc.action.FOO"
private const val ACTION_BAZ = "com.zhangyugehu.androidabc.action.BAZ"

private const val EXTRA_PARAM1 = "com.zhangyugehu.androidabc.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.zhangyugehu.androidabc.extra.PARAM2"

class AsyncWorkerService : IntentService("AsyncWorkerService") {

    class UIHandler(host: AsyncWorkerService): Handler() {
        companion object {
            const val WHAT_TOAST = 0x001
            const val WHAT_LOAD_IMAGE_SUCCESS = 0x002
        }
        private var mServiceRef: WeakReference<AsyncWorkerService> = WeakReference(host)

        fun toast(text: String?){
            sendMessage(this.obtainMessage(WHAT_TOAST, text))
        }

        override fun handleMessage(msg: Message) {
            val service: AsyncWorkerService = mServiceRef.get() ?: return
            when(msg.what){
                WHAT_TOAST-> {
                    var message = msg.obj?:return
                    service.toast(message.toString())
                }
                WHAT_LOAD_IMAGE_SUCCESS->{
                    var file = msg.obj?:return
                    service.toast("文件 ${(file as File).name} 下载成功")
                }
            }
        }
    }


    private val mHandler:UIHandler = UIHandler(this)

    override fun onHandleIntent(intent: Intent?) {
        val param1 = intent?.getStringExtra(EXTRA_PARAM1)?:return
        when (intent?.action) {
            ACTION_FOO -> handleActionFoo(param1)
            ACTION_BAZ -> handleActionBaz(param1)
        }
    }

    private fun toast(text: String){
        Log.d(TAG, "Thread-${Thread.currentThread().name} ")
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
    private fun handleActionFoo(param1: String) {
        Log.d(TAG, "Thread-${Thread.currentThread().name} ")
        AppHttpClient.instance.get("https://www.jianshu.com/p/da4a806e599b", object : Callback{
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }

        })
//        SystemClock.sleep(5000)
//        var text = "handleActionFoo $param1"
//        mHandler.toast(text)
    }

    private fun handleActionBaz(param1: String?) {
        val url = param1?:return mHandler.toast("url 为空")
        try {
            val file = Glide.with(this).asFile().load(url).submit().get()
            mHandler.sendMessage(mHandler.obtainMessage(UIHandler.WHAT_LOAD_IMAGE_SUCCESS, file))
        }catch (e: Exception){
            mHandler.toast(e.message?:"Exception")
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    companion object {
        const val TAG = "AsyncWorkerService"
        @JvmStatic
        fun startActionFoo(context: Context, param1: String) {
            val intent = Intent(context, AsyncWorkerService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
            }
            context.startService(intent)
        }

        @JvmStatic
        fun startActionDownload(context: Context, url: String) {
            val intent = Intent(context, AsyncWorkerService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, url)
            }
            context.startService(intent)
        }
    }
}
