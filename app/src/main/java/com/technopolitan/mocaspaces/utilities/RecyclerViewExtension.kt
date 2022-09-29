package com.technopolitan.mocaspaces.utilities

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller

fun RecyclerView.loadMore(callBack: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            if (adapter != null && linearLayoutManager != null) {
                if (adapter?.itemCount != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter?.itemCount!! - 1
                ) callBack()
            }
        }
    })
}

fun RecyclerView.setSmoothScroll(context: Context) : SmoothScroller{
    return object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}

fun RecyclerView.attachSmoothScroll(smoothScroller: SmoothScroller){
    layoutManager?.startSmoothScroll(smoothScroller)
}