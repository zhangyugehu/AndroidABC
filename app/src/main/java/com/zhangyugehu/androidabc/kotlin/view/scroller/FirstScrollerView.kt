package com.zhangyugehu.androidabc.kotlin.view.scroller

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.Scroller

class FirstScrollerView : View {

    companion object {
        private const val TAG = "FirstScrollerView"
    }
    private var bitmap: Bitmap? = null
    private var scroller: Scroller
    private var velocityTracker: VelocityTracker

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        bitmap = BitmapFactory.decodeStream(context.assets.open("small.png"))
        scroller = Scroller(context)
        velocityTracker = VelocityTracker.obtain()
        setOnClickListener {
            it.scrollBy(20, 20)
        }
    }

    private var firstX = 0F
    private var firstY = 0F
    private var sumX = 0
    private var sumY = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        velocityTracker.addMovement(event)
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                scroller.forceFinished(true)
                firstX = event.x
                firstY = event.y
            }
            MotionEvent.ACTION_MOVE->{
//                scrollTo(sumX + firstX.toInt() - event.x.toInt(),
//                        sumY + firstY.toInt() -event.y.toInt())
                scrollTo(0, sumY + firstY.toInt() -event.y.toInt())
            }
            MotionEvent.ACTION_UP->{
                sumX = scrollX
                sumY = scrollY

                // startX, startY 移动到的位置
                // dx, dy 动画运动到的位置
//                scroller.startScroll(scrollX, scrollY, -scrollX, -scrollY)

                velocityTracker.computeCurrentVelocity(500, Float.MAX_VALUE)
                val xVelocity: Int = velocityTracker.xVelocity.toInt()
                val yVelocity: Int = velocityTracker.yVelocity.toInt()
                scroller.fling(sumX, sumY , -xVelocity, -yVelocity,
                        -Int.MAX_VALUE, Int.MAX_VALUE, -Int.MAX_VALUE, Int.MAX_VALUE)
                invalidate()
            }
        }
        return true
    }

    private var isFling = false
    override fun computeScroll() {
        super.computeScroll()
        if(scroller.computeScrollOffset()){
            isFling = true
            Log.d(TAG, "computeScrollOffset x: ${scroller.currX}, y: ${scroller.currY}")
//            scrollTo(scroller.currX, scroller.currY)
            scrollTo(0, scroller.currY)
        }else{
            if(isFling){
                sumX = scrollX
                sumY = scrollY
            }
            isFling = false
        }
    }

    override fun onDetachedFromWindow() {
        velocityTracker.recycle()
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0F, 0F, null)
    }

}