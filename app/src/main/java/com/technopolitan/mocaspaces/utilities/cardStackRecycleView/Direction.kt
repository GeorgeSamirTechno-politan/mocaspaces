package com.technopolitan.mocaspaces.utilities.cardStackRecycleView


enum class Direction {
    Left, Right, Top, Bottom;

    companion object {
        val HORIZONTAL = listOf(Left, Right)
        val LEFT_ONLY = listOf(Left)
        val RIGHT_ONLY = listOf(Right)
        val VERTICAL = listOf(Top, Bottom)
        val FREEDOM = listOf(*values())
    }
}

