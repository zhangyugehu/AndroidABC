package com.zhangyugehu.androidabc.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ocnyang.pagetransformerhelp.cardtransformer.AlphaAndScalePageTransformer;
import com.ocnyang.pagetransformerhelp.cardtransformer.RotateDownPageTransformer;
import com.ocnyang.pagetransformerhelp.transformer.ParallaxTransformer;
import com.ocnyang.pagetransformerhelp.transformer.ScaleInOutTransformer;
import com.ocnyang.pagetransformerhelp.transformer.ZoomOutSlideTransformer;
import com.zhangyugehu.androidabc.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    private static final String TAG = "BannerActivity";

    private ViewPager mViewPager;
    private List<View> mBannerViews;

    private String[] urls = {
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3963827678,2174767167&fm=200&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1140450140,4104512530&fm=200&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2495085981,2140583333&fm=200&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4224600805,2363683691&fm=200&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3790646922,1664677246&fm=26&gp=0.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mBannerViews = new ArrayList<>();
        initPageViews();
        mViewPager = findViewById(R.id.vp_banner);
        mViewPager.setPageMargin(30);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mBannerViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }


            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = mBannerViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mBannerViews.get(position));
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return super.getItemPosition(object);
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mCurrPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int pagerCount = mViewPager.getAdapter().getCount();
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mCurrPosition == pagerCount - 1) {
                        mViewPager.setCurrentItem(1, false);
                    } else if (mCurrPosition == 0) {
                        mViewPager.setCurrentItem(pagerCount - 2, false);
                    }
                }
            }
        });
        mViewPager.setCurrentItem(1);
    }

    private void initPageViews() {
        mBannerViews.add(getImageView(urls.length - 1));
        for (int i = 0; i < 5; i++) {
            mBannerViews.add(getImageView(i));
        }
        mBannerViews.add(getImageView(0));
    }

    private View getImageView(int position) {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(BannerActivity.this)
                .load(urls[position]).apply(new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher))
                .into(imageView);
        return imageView;
    }
}
