package com.abc.nestedscrolltest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

class NestedHorizontalScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : HorizontalScrollView(context, attributeSet, defStyle), NestedScrollingChild3 {

    private val nestedScrollHelper = NestedScrollingChildHelper(this)
    private val mScrollOffset = IntArray(2)
    private val mConsumedOffset = IntArray(2)

    init {
        nestedScrollHelper.isNestedScrollingEnabled = true
        overScrollMode = OVER_SCROLL_NEVER
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        super.setNestedScrollingEnabled(enabled)
        nestedScrollHelper.isNestedScrollingEnabled = enabled
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return nestedScrollHelper.startNestedScroll(axes, type)
    }

    private var mLastTouchX = 0
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent?.requestDisallowInterceptTouchEvent(true)
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL,ViewCompat.TYPE_TOUCH)
                mLastTouchX = ev.x.toInt()
                super.onTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = mLastTouchX - x;
                 when{
                    (dx > 0 && !canScrollHorizontally(1)) -> {
                        dispatchNestedScroll(0,0,dx,0,mScrollOffset,ViewCompat.TYPE_TOUCH)
                    }
                    (dx < 0 && !canScrollHorizontally(-1)) -> {
                        dispatchNestedScroll(0,0,dx,0,mScrollOffset,ViewCompat.TYPE_TOUCH)
                    }
                    else -> {
                        if(!dispatchNestedPreScroll(dx,0,mConsumedOffset,mScrollOffset,ViewCompat.TYPE_TOUCH)){
                            super.onTouchEvent(ev)
                        }
                    }
                }
                mLastTouchX = x - mScrollOffset[0]
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {
                parent?.requestDisallowInterceptTouchEvent(false)
                stopNestedScroll(ViewCompat.TYPE_TOUCH)
                super.onTouchEvent(ev)
            }
            else -> {
                super.onTouchEvent(ev)
            }
        }
        return true
    }

    override fun stopNestedScroll(type: Int) {
        nestedScrollHelper.stopNestedScroll(type)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return nestedScrollHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return nestedScrollHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return nestedScrollHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun stopNestedScroll() {
        nestedScrollHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return nestedScrollHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        nestedScrollHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type,
            consumed
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return nestedScrollHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }
}