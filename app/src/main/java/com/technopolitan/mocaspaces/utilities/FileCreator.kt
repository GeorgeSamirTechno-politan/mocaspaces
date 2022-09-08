package com.technopolitan.mocaspaces.utilities


object FileCreator {

    const val pngFormat = ".png"

    fun createTempFile(fileFormat: String) =
        createTempFile(System.currentTimeMillis().toString(), fileFormat)

}