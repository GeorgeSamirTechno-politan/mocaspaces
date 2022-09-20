package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.SearchHintResponse

class SearchHintMapper {

    var id: Int? = null
    var type: Int? = null
    var name: String = ""
    val spaceTypeMapper: SpaceTypeMapper = SpaceTypeMapper()

    fun init(response: SearchHintResponse): SearchHintMapper {
        response.let {
            id = it.id
            type = it.type
            name = it.name
            it.spaceTypeResponseList.forEach { item ->
                if (item.id == 1)
                    spaceTypeMapper.hasWorkSpace = item.hasValues
                if (item.id == 2)
                    spaceTypeMapper.hasMeeting = item.hasValues
                if (item.id == 3)
                    spaceTypeMapper.hasEvent = item.hasValues
                if (item.id == 4)
                    spaceTypeMapper.hasBizLounge = item.hasValues
            }
        }
        return this
    }

    fun initEmpty(emptyString: String): SearchHintMapper {
        id = null
        type = null
        name = emptyString
        return this
    }
}