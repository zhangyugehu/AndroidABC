package com.zhangyugehu.androidabc.bigimage;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zhangyugehu.androidabc.R;

public class BlogBigImageActivity extends AppCompatActivity {

    private SubsamplingScaleImageView subsamplingScaleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        subsamplingScaleImageView = findViewById(R.id.sub_sampling_scale_image_view);

        subsamplingScaleImageView.setDebug(true);
        subsamplingScaleImageView.setImage(ImageSource.asset("card.png"), new ImageViewState(1.0f, new PointF(0,0), 0));
    }
}
