package com.zhangyugehu.androidabc.kotlin.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebClient extends WebChromeClient {
    private Activity context;

    public MyWebClient(Activity context) {
        this.context = context;
    }

    // For Android 5.0+
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback,
                                     android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
        //打开图库
        return false;
    }
    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg) {
        //打开图库
    }
    //3.0--版本
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        openFileChooser(uploadMsg);
    }
    // For Android 4.1
    public void openFileChooser(ValueCallback uploadMsg, String acceptType,
                                String capture) {
        openFileChooser(uploadMsg);
    }
}
