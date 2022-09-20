package com.technopolitan.mocaspaces.data.home

import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper


data class HomeSearchMapper(
    var color: Int,
    var hintSearch: String,
    var itemIdWithPosition: Int = 0,
    val textInputLayoutBoxColor: Int,
    val searchBackgroundDrawable: Int,
    var searchHintMapper: SearchHintMapper = SearchHintMapper()
)