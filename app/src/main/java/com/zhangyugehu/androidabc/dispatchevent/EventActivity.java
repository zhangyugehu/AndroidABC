package com.zhangyugehu.androidabc.dispatchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.zhangyugehu.androidabc.R;

public class EventActivity extends AppCompatActivity {
    private static final String TAG = "EventActivity";

    private CustomViewGroup customViewGroup;
    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        customViewGroup = findViewById(R.id.custom_view_group);
        customView = findViewById(R.id.custom_view);

        customViewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG, "customViewGroup onTouch: " + motionEvent.getAction());
                return false;
            }
        });
        customViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "customViewGroup onClick: ");
            }
        });

        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG, "customView onTouch: " + motionEvent.getAction());
                return false;
            }
        });
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "customView onClick: ");
            }
        });

//        customViewGroup.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "EventActivity onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }

    public static class CustomViewGroup extends FrameLayout{

        public CustomViewGroup(@NonNull Context context) {
            super(context);
        }

        public CustomViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            Log.d(TAG, "CustomViewGroup onInterceptTouchEvent: " + ev.getAction());
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.d(TAG, "CustomViewGroup onTouchEvent: " + event.getAction());
            return false;
        }

        @Override
        public boolean performClick() {
            Log.d(TAG, "CustomViewGroup performClick: ");
            return super.performClick();
        }
    }

    public static class CustomView extends View{

        private Paint mPaint;
        private Point mCenterPoint;
        public CustomView(Context context) {
            this(context, null);
        }

        public CustomView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mPaint = new Paint();
//            mCenterPoint = new Point(0,0);
            mPaint.setColor(Color.BLUE);
        }

        float startX, startY;
        Point mLastCenterPoint;
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.d(TAG, "CustomView onTouchEvent: " + event.getAction());
            if(mCenterPoint.x < getWidth() && mCenterPoint.y<getHeight()
                    && mCenterPoint.x > 0 && mCenterPoint.y > 0 ){
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    mCenterPoint.x = (int) (mLastCenterPoint.x + (event.getX() - startX));
                    mCenterPoint.y = (int) (mLastCenterPoint.y + (event.getY() - startY));
                }else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    startX = event.getX();
                    startY = event.getY();
                    mLastCenterPoint = new Point(mCenterPoint);
                }
                postInvalidate();
                return true;
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                mCenterPoint.x = getWidth()/2;
                mCenterPoint.y = getHeight()/2;
                postInvalidate();
            }
            return false;
        }

        @Override
        public boolean performClick() {
            Log.d(TAG, "CustomView performClick: ");
            return super.performClick();
        }

        int mHeight, mWidth;
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mHeight = getMeasuredHeight();
            mWidth = getMeasuredWidth();
            mCenterPoint = new Point(mWidth/2, mHeight/2);
            postInvalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, getWidth()/4, mPaint);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mPaint = null;
        }
    }
}
