package com.zhangyugehu.androidabc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.zhangyugehu.androidabc.kotlin.intentservice.AsyncWorkerService
import kotlinx.android.synthetic.main.activity_kotlin.*
import kotlinx.android.synthetic.main.kotlin_recycle_item_kotlin.view.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        initView()
        initData(1)
    }

    var mData: ArrayList<String> = ArrayList()
    private fun initData(tabIdx: Int) {
        mData.clear()
        for(i in 1..100){
            mData.add("""Tab $tabIdx, Row $i""")
        }
    }

    private val mAdapter = object : RecyclerView.Adapter<ViewHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(layoutInflater.inflate(R.layout.kotlin_recycle_item_kotlin, p0, false))
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setData(mData[position])
        }

    }

    private fun initView() {
        toolbar.title = "TabLay"
        setSupportActionBar(toolbar)
        recyclerView.let {
            it.itemAnimator = DefaultItemAnimator()
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = mAdapter
        }
        for (i in 1..10){
            tabLayout.addTab(tabLayout.newTab().setText("""TAB $i"""))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: 0
                initData(position + 1)
                mAdapter.notifyDataSetChanged()
                toast(position.toString())
            }
        })
    }

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(data: String) {
            itemView.let {
                it.title.text = data
                it.main.setOnClickListener{
                    AsyncWorkerService.startActionFoo(this@KotlinActivity, data)
//                    AsyncWorkerService.startActionDownload(this@KotlinActivity, "https://upload-images.jianshu.io/upload_images/2720645-751647c1fa5d0b0f.png")
                }
            }
        }
    }
}


