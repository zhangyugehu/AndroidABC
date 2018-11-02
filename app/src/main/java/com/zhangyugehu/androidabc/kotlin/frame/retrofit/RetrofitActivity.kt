package com.zhangyugehu.androidabc.kotlin.frame.retrofit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zhangyugehu.androidabc.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
    }

    fun netRequestWithRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        val repos = apiService.listRepos("zhangyugehu")
        repos.enqueue(object : Callback<List<String>> {
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
            }
        })
    }
}
