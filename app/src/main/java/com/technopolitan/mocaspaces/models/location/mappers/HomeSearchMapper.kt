package com.technopolitan.mocaspaces.models.location.mappers


data class HomeSearchMapper(
    var color: Int,
    var hintSearch: String,
    var itemIdWithPosition: Int = 0,
    val textInputLayoutBoxColor: Int,
    val searchBackgroundDrawable: Int,
    var searchHintMapper: SearchHintMapper = SearchHintMapper()


) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HomeSearchMapper

        if (color != other.color) return false
        if (hintSearch != other.hintSearch) return false
        if (itemIdWithPosition != other.itemIdWithPosition) return false
        if (textInputLayoutBoxColor != other.textInputLayoutBoxColor) return false
        if (searchBackgroundDrawable != other.searchBackgroundDrawable) return false
        if (searchHintMapper != other.searchHintMapper) return false

        return true
    }

    override fun hashCode(): Int {
        var result = color
        result = 31 * result + hintSearch.hashCode()
        result = 31 * result + itemIdWithPosition
        result = 31 * result + textInputLayoutBoxColor
        result = 31 * result + searchBackgroundDrawable
        result = 31 * result + searchHintMapper.hashCode()
        return result
    }
}