package com.technopolitan.mocaspaces.utilities

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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