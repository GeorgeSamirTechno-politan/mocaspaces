package com.technopolitan.mocaspaces.data

data class DropDownMapper(
    val id: Int, val name: String, val image: String, var selected: Boolean = false,
    var description: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DropDownMapper

        if (id != other.id) return false
        if (name != other.name) return false
        if (image != other.image) return false
        if (selected != other.selected) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + selected.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}