package com.technopolitan.mocaspaces.data

data class DropDownMapper(val id: Int, val name: String, val image: String, var selected: Boolean = false,
var description: String)