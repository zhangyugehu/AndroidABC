package com.zhangyugehu.androidabc.kotlin.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zhangyugehu.androidabc.R
import android.os.Build
import android.support.annotation.RequiresApi
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.*
import android.widget.Toast
import android.content.ActivityNotFoundException




class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.web_view)

        webView.webChromeClient= object : WebChromeClient() {
            @SuppressLint("NewApi")
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams): Boolean {
                this@WebViewActivity.filePathCallback = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(intent, 123)
                } catch (e: ActivityNotFoundException) {
                    this@WebViewActivity.filePathCallback = null

                    Toast.makeText(this@WebViewActivity, "hhhh", Toast.LENGTH_LONG).show()
                    return false
                }
                return true
            }
        }
        webView.settings.javaScriptEnabled=true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.loadWithOverviewMode = true
        webView.settings.allowContentAccess = true


        webView.loadUrl("https://18519539963.udesk.cn/im_client/?c_phone=f4630583007032571d8d7905d0a3a2a4b56244a9f470d6e36a2b4f28046bb2a7d07c2a10225eb3a6a2484cd488b08051&nonce=5GXQAQZFQZDP6W9B&signature=5C49BA714D3FE023D55508B47B0BCC6FCADBCDFB&timestamp=1540964686357&web_token=17601000633")
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123){
            Log.d("WebViewActivity", data?.data.toString())
            this.filePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data))
        }
    }

}
