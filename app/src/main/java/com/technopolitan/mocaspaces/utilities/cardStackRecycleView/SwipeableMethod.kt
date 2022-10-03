package com.technopolitan.mocaspaces.utilities.cardStackRecycleView

enum class SwipeableMethod {
    AutomaticAndManual,
    Automatic,
    Manual,
    None;

    fun canSwipe(): Boolean = canSwipeAutomatically() || canSwipeManually()

    fun canSwipeAutomatically(): Boolean = this == AutomaticAndManual || this == Automatic

    fun canSwipeManually(): Boolean = this == AutomaticAndManual || this == Manual

}