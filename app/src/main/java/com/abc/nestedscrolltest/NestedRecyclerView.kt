package com.abc.nestedscrolltest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.NestedScrollingParent3
import androidx.recyclerview.widget.RecyclerView

class NestedRecyclerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attributeSet, defStyle), NestedScrollingParent3 {

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and View.SCROLL_AXIS_HORIZONTAL != 0
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return axes and View.SCROLL_AXIS_HORIZONTAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }

    override fun onStopNestedScroll(target: View, type: Int) {

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
        scrollBy(dxUnconsumed, 0)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
//        scrollBy(dxUnconsumed, 0)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {

    }
}