package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.SearchHintResponse

class SearchHintMapper {

    var id: Int = 0
    var type: Int = 0
    var name: String = ""

    fun init(response: SearchHintResponse): SearchHintMapper {
        response.let {
            id = it.id
            type = it.type
            name = it.name
        }
        return this
    }

    fun initEmpty(emptyString: String): SearchHintMapper {
        id = -1
        type = -1
        name = emptyString
        return this
    }
}