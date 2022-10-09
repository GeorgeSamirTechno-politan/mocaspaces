package com.technopolitan.mocaspaces.utilities.customDotPageIndicator

import androidx.recyclerview.widget.RecyclerView

class DotRecyclerDataObserver(private val dotPageIndicator: DotPageIndicator) :
    RecyclerView.AdapterDataObserver() {


    override fun onChanged() {
        dotPageIndicator.updateIndicatorsCount()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        dotPageIndicator.updateIndicatorsCount()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        dotPageIndicator.updateIndicatorsCount()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        dotPageIndicator.updateIndicatorsCount()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        dotPageIndicator.updateIndicatorsCount()
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        dotPageIndicator.updateIndicatorsCount()
    }
}