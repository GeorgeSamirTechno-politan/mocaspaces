package com.technopolitan.mocaspaces.data.home


data class HomeSearchMapper(
    var color: Int,
    var hintSearch: String,
    var itemIdWithPosition: Int = 0,
    val textInputLayoutBoxColor: Int
)