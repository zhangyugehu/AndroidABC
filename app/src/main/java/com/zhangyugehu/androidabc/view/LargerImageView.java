package com.zhangyugehu.androidabc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhangyugehu.androidabc.view.event.MoveGestureDetector;

import java.io.IOException;
import java.io.InputStream;

public class LargerImageView extends View {

    private BitmapRegionDecoder mDecoder;
    private MoveGestureDetector mDetector;

    private static final BitmapFactory.Options mOptions = new BitmapFactory.Options();

    static {
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    private Rect mImageBounds = new Rect();
    private Rect mViewRect = new Rect();
    private Rect mPrevViewRect = new Rect();

    private HandlerThread mHandlerThread;
    private Handler mThreadHandler;
    private Handler mMainHandler = new Handler();

    public LargerImageView(Context context) {
        this(context, null);
    }

    public LargerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void handlerInvalidate() {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    public void setImageStream(InputStream is) {
        try {
            mDetector = new MoveGestureDetector(getContext(),
                    new MoveGestureDetector.SimpleMoveGestureDetector() {
                        @Override
                        public boolean onMove(MoveGestureDetector detector) {
                            int moveX = (int) detector.getMoveX();
                            int moveY = (int) detector.getMoveY();

                            if (mImageBounds.right > getWidth()) {
                                mViewRect.offset(-moveX, 0);
                                checkWidth();
                                mThreadHandler.sendEmptyMessage(0);
                            }
                            if (mImageBounds.bottom > getHeight()) {
                                mViewRect.offset(0, -moveY);
                                checkHeight();
                                mThreadHandler.sendEmptyMessage(0);
                            }
                            return true;
                        }
                    });
            mHandlerThread = new HandlerThread("regionBitmapDecoderThread");
            mHandlerThread.start();
            mThreadHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what != 0) return;
                    try {
                        if (mPrevViewRect.left == mViewRect.left &&
                                mPrevViewRect.right == mViewRect.right &&
                                mPrevViewRect.top == mViewRect.top &&
                                mPrevViewRect.bottom == mViewRect.bottom)
                            return;
                        System.out.println(mViewRect);
                        mRegionBitmap = mDecoder.decodeRegion(mViewRect, mOptions);
                        mPrevViewRect.left = mViewRect.left;
                        mPrevViewRect.top = mViewRect.top;
                        mPrevViewRect.right = mViewRect.right;
                        mPrevViewRect.bottom = mViewRect.bottom;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handlerInvalidate();
                }
            };


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            mImageBounds.left = 0;
            mImageBounds.top = 0;
            mImageBounds.right = options.outWidth;
            mImageBounds.bottom = options.outHeight;
//            System.out.println(options.outWidth +  " _ " + options.outHeight);


            requestLayout();
//            invalidate();
            mThreadHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void checkWidth() {


        Rect rect = mViewRect;
        int imageWidth = mImageBounds.right;

        if (rect.right > imageWidth) {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }


    private void checkHeight() {

        Rect rect = mViewRect;
        int imageHeight = mImageBounds.bottom;

        if (rect.bottom > imageHeight) {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onToucEvent(event);
        return true;
    }

    private Bitmap mRegionBitmap = null;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRegionBitmap != null) {
            canvas.drawBitmap(mRegionBitmap, 0, 0, null);
            mRegionBitmap.recycle();
            mRegionBitmap = null;
        }
//        canvas.drawBitmap(mDecoder.decodeRegion(mViewRect, mOptions), 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewRect.left = 0;
        mViewRect.top = 0;
        mViewRect.right = getMeasuredWidth();
        mViewRect.bottom = getMeasuredHeight();

        if(mThreadHandler != null) {
            mThreadHandler.sendEmptyMessage(0);
        }

    }
}
