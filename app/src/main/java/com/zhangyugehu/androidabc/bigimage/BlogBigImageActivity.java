package com.zhangyugehu.androidabc.bigimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zhangyugehu.androidabc.R;

public class BlogBigImageActivity extends AppCompatActivity {

    private SubsamplingScaleImageView subsamplingScaleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        subsamplingScaleImageView = findViewById(R.id.sub_sampling_scale_image_view);

        subsamplingScaleImageView.setImage(ImageSource.asset("android_tree.png"));
    }
}
