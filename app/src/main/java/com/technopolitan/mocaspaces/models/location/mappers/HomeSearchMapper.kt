package com.technopolitan.mocaspaces.models.location.mappers


data class HomeSearchMapper(
    var color: Int,
    var hintSearch: String,
    var itemIdWithPosition: Int = 0,
    val textInputLayoutBoxColor: Int,
    val searchBackgroundDrawable: Int,
    var searchHintMapper: SearchHintMapper = SearchHintMapper()


) {
    override fun toString(): String {
        return "HomeSearchMapper(color=$color," +
                " hintSearch='$hintSearch'," +
                " itemIdWithPosition=$itemIdWithPosition," +
                " textInputLayoutBoxColor=$textInputLayoutBoxColor," +
                " searchBackgroundDrawable=$searchBackgroundDrawable," +
                " searchHintMapper=$searchHintMapper)"
    }
}