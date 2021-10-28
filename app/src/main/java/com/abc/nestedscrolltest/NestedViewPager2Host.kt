package com.abc.nestedscrolltest

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class NestedViewPager2Host @JvmOverloads constructor(context: Context, attSet: AttributeSet? = null, defStyle:Int = 0) : FrameLayout(context,attSet,defStyle),NestedScrollingParent3 {
    private val child: ViewPager2? get() = if (childCount > 0) getChildAt(0) as? ViewPager2 else null
    private var startDragView:View? = null

    private fun startFakeDrag(){
        child?.let {
            if (!it.isFakeDragging) {
                val helper = getSnapHelper(it)
                val recycler = it.getChildAt(0) as RecyclerView
                startDragView = helper.findSnapView(recycler.layoutManager)
                it.beginFakeDrag()
            }
        }
    }

    private fun performFakeDragBy(x: Int) {
        child?.fakeDragBy(-x.toFloat())
    }

    private fun endFakeDrag(){
        child?.endFakeDrag()
        startDragView = null
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and View.SCROLL_AXIS_HORIZONTAL != 0
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return axes and View.SCROLL_AXIS_HORIZONTAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        startFakeDrag()
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (type != ViewCompat.TYPE_TOUCH) {
            return
        }
        if (target !is NestedHorizontalScrollView) {
            return
        }
        child?.let {
            val recycler = it.getChildAt(0) as? RecyclerView
            val snapHelper = getSnapHelper(it)
            val layoutManager = recycler?.layoutManager
            val startDragView = this.startDragView
            if (layoutManager == null || startDragView == null){
                return@let
            }
            val dis = snapHelper.calculateDistanceToFinalSnap(layoutManager,startDragView)!!.first()
            if (dx < 0 && dis < 0) {
                // left 👈
                val consumedDx = Math.max(dx, dis)
                consumed[0] = consumedDx
                performFakeDragBy(consumedDx)
            } else if (dx > 0 && dis > 0) {
                // right 👉
                val consumedDx = Math.min(dx, dis)
                consumed[0] = consumedDx
                performFakeDragBy(consumedDx)
            }
        }
    }

    private fun getSnapHelper(obj:Any): PagerSnapHelper{
        val field = ViewPager2::class.java.getDeclaredField("mPagerSnapHelper")
        field.isAccessible = true
        return field.get(obj) as PagerSnapHelper
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (type != ViewCompat.TYPE_TOUCH){
            return
        }
        if (target !is NestedHorizontalScrollView){
            return
        }
        performFakeDragBy(dxUnconsumed)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if (type != ViewCompat.TYPE_TOUCH){
            return
        }
        endFakeDrag()
    }

}