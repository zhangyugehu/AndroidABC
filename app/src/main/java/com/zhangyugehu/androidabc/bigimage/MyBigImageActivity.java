package com.zhangyugehu.androidabc.bigimage;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.zhangyugehu.androidabc.R;
import com.zhangyugehu.androidabc.view.LargerImageView;

import java.io.IOException;
import java.io.InputStream;

public class MyBigImageActivity extends AppCompatActivity {

    private static final String TAG = "MyBigImageActivity";
    private LargerImageView lagerImageView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lagerImageView  = findViewById(R.id.larger_image_view);
        imageView  = findViewById(R.id.image_view);
        try {
            InputStream imageStream = getAssets().open("jlk3.jpg");
            lagerImageView.setImageStream(imageStream);
//            imageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
