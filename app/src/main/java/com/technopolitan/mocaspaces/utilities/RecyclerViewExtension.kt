package com.technopolitan.mocaspaces.utilities

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.loadMore(hasMore: Boolean, callBack: () -> Unit) {
    if (hasMore) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (adapter != null) {
                    if (adapter?.itemCount != null) {
                        if (linearLayoutManager != null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter?.itemCount!! - 1
                        ) callBack()
                    }
                }

            }
        })
    }

}