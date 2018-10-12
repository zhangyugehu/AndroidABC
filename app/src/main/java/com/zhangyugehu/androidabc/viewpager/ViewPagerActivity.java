package com.zhangyugehu.androidabc.viewpager;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresFeature;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.zhangyugehu.androidabc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {

    static class PageInfo{
        public PageInfo(String title, View view) {
            this.title = title;
            this.view = view;
        }

        private String title;
        private View view;
    }

    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.pager_tab_strip) PagerTabStrip mPagerTabStrip;

    private ArrayList<PageInfo> mPagerInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);

        initPageInfo();
        initPageStrip();

        mViewPager.setAdapter(new GuidePageAdapter());
    }

    private void initPageStrip() {
        mPagerTabStrip.setDrawFullUnderline(false);
        mPagerTabStrip.setBackgroundResource(R.color.colorPrimary);
        mPagerTabStrip.setTabIndicatorColor(Color.RED);
//        mPagerTabStrip.setTextSpacing(400);
    }

    private void initPageInfo() {
        mPagerInfoContainer = new ArrayList<>();
        mPagerInfoContainer.add(new PageInfo("今日头条", LayoutInflater.from(this).inflate(R.layout.pager_tab1, null)));
        mPagerInfoContainer.add(new PageInfo("今日热点", LayoutInflater.from(this).inflate(R.layout.pager_tab2, null)));
        mPagerInfoContainer.add(new PageInfo("今日财经", LayoutInflater.from(this).inflate(R.layout.pager_tab3, null)));
        mPagerInfoContainer.add(new PageInfo("今日军事", LayoutInflater.from(this).inflate(R.layout.pager_tab4, null)));
    }

    private class GuidePageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagerInfoContainer.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mPagerInfoContainer.get(position).view);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mPagerInfoContainer.get(position).view;
            container.addView(view);
            return view;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mPagerInfoContainer.get(position).title;
        }
    }
}
